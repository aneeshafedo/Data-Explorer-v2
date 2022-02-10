package io.ballerina.dataexplorer;

import com.github.mustachejava.DefaultMustacheFactory;
import com.github.mustachejava.Mustache;
import com.github.mustachejava.MustacheFactory;
import io.ballerina.compiler.api.SemanticModel;
import io.ballerina.compiler.syntax.tree.SyntaxTree;
import io.ballerina.dataexplorer.utils.BalProjectContext;
import io.ballerina.dataexplorer.utils.DataExplorerUtils;
import io.ballerina.projects.BuildOptions;
import io.ballerina.projects.Document;
import io.ballerina.projects.JBallerinaBackend;
import io.ballerina.projects.JvmTarget;
import io.ballerina.projects.Module;
import io.ballerina.projects.PackageCompilation;
import io.ballerina.projects.ProjectEnvironmentBuilder;
import io.ballerina.projects.ProjectException;
import io.ballerina.projects.directory.BuildProject;
import io.ballerina.projects.environment.Environment;
import io.ballerina.projects.environment.EnvironmentBuilder;
import io.ballerina.projects.internal.model.Target;
import io.ballerina.tools.diagnostics.DiagnosticSeverity;
import io.ballerina.tools.text.LineRange;
import org.ballerinalang.annotation.JavaSPIService;
import org.ballerinalang.langserver.commons.service.spi.ExtendedLanguageServerService;
import org.ballerinalang.langserver.commons.workspace.WorkspaceManager;
import org.eclipse.lsp4j.Position;
import org.eclipse.lsp4j.Range;
import org.eclipse.lsp4j.jsonrpc.services.JsonNotification;
import org.eclipse.lsp4j.jsonrpc.services.JsonSegment;
import org.eclipse.lsp4j.services.LanguageServer;
import org.json.JSONArray;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.StringJoiner;
import java.util.concurrent.CompletableFuture;

import static io.ballerina.dataexplorer.DataExplorerConstants.SUCCESS;

/**
 * ..
 */
@JavaSPIService("org.ballerinalang.langserver.commons.service.spi.ExtendedLanguageServerService")
@JsonSegment("dataExplorerService")
public class DataExplorerService implements ExtendedLanguageServerService {

    private WorkspaceManager workspaceManager;
    private Path tempProjectDir;
    private static final String TEMP_DIR_PREFIX = "data-explorer-dir-";
    public static final String BAL_TOML_FILE_NAME = "Ballerina.toml";
    private static final String MAIN_FILE_PREFIX = "main-";
    public static final String BAL_FILE_EXT = ".bal";
    private static final Logger LOGGER = LoggerFactory.getLogger(DataExplorerService.class);

    @Override
    public void init(LanguageServer langServer, WorkspaceManager workspaceManager) {
        this.workspaceManager = workspaceManager;
    }

    @Override
    public Class<?> getRemoteInterface() {
        return getClass();
    }

    //getQueryOutput
    @JsonNotification
    public CompletableFuture<DataExplorerResponse> getResults(DataExplorerRequest request) throws Exception {
        return CompletableFuture.supplyAsync(() -> {
            DataExplorerResponse dataExplorerResponse = null;
            try {
                String mainBalFile = getMainFileContent(request);
                BuildProject project = createProject(mainBalFile, request.getBallerinaTomlFilePath());
                Path executablePath = createExecutables(project);
                String result = executeJar(executablePath.toAbsolutePath().toString());
                JSONArray jsonArr = new JSONArray(result);
                dataExplorerResponse = new DataExplorerResponse(jsonArr);
            } catch (Exception e) {
                LOGGER.error(e.getMessage());
            }
            return dataExplorerResponse;
        });
    }

    @JsonNotification
    public  CompletableFuture<List<DBOperationFinderResponse>> getRemoteFunctionCalls
            (DBOperationFinderRequest request) {
        return CompletableFuture.supplyAsync(() -> {
            List<DBOperationFinderResponse> callFinderResponses = new ArrayList<>(); //give the length of the array list
            String fileUri = request.getDocumentIdentifier().getUri();
            Path path = Path.of(fileUri);
            Optional<SemanticModel> semanticModel = this.workspaceManager.semanticModel(path);
            Optional<Module> module = this.workspaceManager.module(path);

            if (semanticModel.isEmpty() || module.isEmpty()) {
                return callFinderResponses;
            }

            DBOperationFinder nodeVisitor = new DBOperationFinder();

            Optional<Document> document = this.workspaceManager.document(path);

            if (document.isEmpty()) {
                return callFinderResponses;
            }

            SyntaxTree syntaxTree = document.get().syntaxTree();
            syntaxTree.rootNode().accept(nodeVisitor);
            List<CodeLensePos> connectorCallList = nodeVisitor.getConnectors();

            for (CodeLensePos connectorCall : connectorCallList) {
                LineRange range = connectorCall.getLineRange();
                Range lineRange = new Range(new Position(range.startLine().line(), range.startLine().offset()),
                        new Position(range.endLine().line(), range.endLine().offset()));
                DBOperationFinderResponse response = new DBOperationFinderResponse();
                response.setRemoteCallPos(lineRange);
                response.setType(SUCCESS);
                response.setMessage(SUCCESS);
                callFinderResponses.add(response);
            }
            return callFinderResponses;
        });
    }

    private String executeJar(String executablePath) throws IOException, InterruptedException {
        Process proc = Runtime.getRuntime().exec("java -jar " + executablePath);
        proc.waitFor();
        // Then retreive the process output
        InputStream in = proc.getInputStream();
        byte[] b = new byte[in.available()];
//        int read = in.read(b, 0, b.length);
        return new String(b);

//        InputStream err = proc.getErrorStream();
//
//        byte c[]=new byte[err.available()];
//        err.read(c,0,c.length);

    }

    private String getMainFileContent(DataExplorerRequest request) throws IOException {
        MustacheFactory mf = new DefaultMustacheFactory();
        Mustache mustache = mf.compile("template.main.mustache");
        Map<String, Object> context = new HashMap<>();

        Collection<String> imports = new ArrayList<>();
        if (request.getImportsList().size() > 0) {
            for (String balImport : request.getImportsList()) {
                imports.add(String.format("import %s;", balImport));
            }
        }
        Collection<String> variables = new ArrayList<>();
        if (request.getVariables().size() > 0) {
            request.getVariables().forEach(var -> variables.add(var + ";"));
        }

        String clientInitiParams = "";
        if (request.getParams().size() > 0) {
            clientInitiParams = String.join(", " , request.getParams());
        }

        BalProjectContext templateContext = new BalProjectContext(imports, clientInitiParams, variables,
                request.getQueryString(), request.getClientType());
        context.put("mainBal", templateContext);
        StringWriter writer = new StringWriter();

        mustache.execute(writer, context).flush();
        return writer.toString();
    }

    /**
     * Creates and returns a 'BuildProject' instance which contains the given source snippet in the main file.
     *
     * @param mainBalContent Source file content to be used for generating the project
     * @return Created Ballerina project
     */
    private BuildProject createProject(String mainBalContent, String tomlFilePath) throws DataExplorerException {
        try {
            // Creates a new directory in the default temporary file directory.
            this.tempProjectDir = Files.createTempDirectory(TEMP_DIR_PREFIX + System.currentTimeMillis());
            this.tempProjectDir.toFile().deleteOnExit();

            // Creates a main file and writes the generated code snippet.
            createMainBalFile(mainBalContent);
            // Creates the Ballerina.toml file and writes the package meta information.
            createBallerinaToml(tomlFilePath);

            Path userHome = Paths.get("build").resolve("user-home"); // check if set earlier
            Environment environment = EnvironmentBuilder.getBuilder().setUserHome(userHome).build();
            ProjectEnvironmentBuilder projectEnvironmentBuilder = ProjectEnvironmentBuilder.getBuilder(environment);
            BuildOptions buildOptions = BuildOptions.builder().setOffline(false).build(); // offline false or true

            return BuildProject.load(projectEnvironmentBuilder, this.tempProjectDir, buildOptions);
        } catch (Exception e) {
            throw new DataExplorerException("Error occurred while creating a temporary evaluation project at: " +
                    this.tempProjectDir + ", due to: " + e.toString());
        }
    }

    /**
     * Helper method to write a string source to a file.
     *
     * @param content Content to write to the file.
     * @throws IOException If writing was unsuccessful.
     */
    private void createMainBalFile(String content) throws DataExplorerException, IOException {
        File mainBalFile = File.createTempFile(MAIN_FILE_PREFIX, BAL_FILE_EXT, tempProjectDir.toFile());
        mainBalFile.deleteOnExit();
        DataExplorerUtils.writeToFile(mainBalFile, content);
    }

    /**
     * Helper method to create a Ballerina.toml file with predefined content.
     *
     * @throws IOException If writing was unsuccessful.
     */
    private void createBallerinaToml(String balTomlFilePathStr) throws DataExplorerException, IOException {
        Path ballerinaTomlPath = tempProjectDir.resolve(BAL_TOML_FILE_NAME);
        File balTomlFile = Files.createFile(ballerinaTomlPath).toFile();
        balTomlFile.deleteOnExit();

        Path balTomlFilePath = Path.of(balTomlFilePathStr);
        String balTomlContent = Files.readString(balTomlFilePath, StandardCharsets.US_ASCII);

        DataExplorerUtils.writeToFile(balTomlFile, balTomlContent);
    }

//    private PackageCompilation compile(Project project) throws DataExplorerException {
//        try {
//            PackageCompilation pkgCompilation = project.currentPackage().getCompilation();
//            validateForCompilationErrors(pkgCompilation);
//            return pkgCompilation;
//        } catch (ProjectException | DataExplorerException e) {
//            throw new DataExplorerException("failed to create executables while evaluating expression: "
//                    + e.getMessage());
//        }
//    }

    /* Compilation methods */

    /**
     * Creates a Ballerina executable jar on the generated code snippet, in a temp directory.
     *
     * @param project build project instance
     * @return path of the created executable JAR
     */
    private Path createExecutables(BuildProject project) throws DataExplorerException {
        Target target;
        try {
            target = new Target(project.sourceRoot());
        } catch (IOException | ProjectException e) {
            throw new DataExplorerException("failed to resolve target path while evaluating expression: "
                    + e.getMessage());
        }

        Path executablePath;
        try {
            executablePath = target.getExecutablePath(project.currentPackage()).toAbsolutePath().normalize();
        } catch (IOException e) {
            throw new DataExplorerException("failed to create executables while evaluating expression: "
                    + e.getMessage());
        }

        try {
            PackageCompilation pkgCompilation = project.currentPackage().getCompilation();
            validateForCompilationErrors(pkgCompilation);
            JBallerinaBackend jBallerinaBackend = JBallerinaBackend.from(pkgCompilation, JvmTarget.JAVA_11);
            jBallerinaBackend.emit(JBallerinaBackend.OutputType.EXEC, executablePath);
        } catch (ProjectException e) {
            throw new DataExplorerException("failed to create executables while evaluating expression: "
                    + e.getMessage());
        }
        return executablePath;
    }

    private void validateForCompilationErrors(PackageCompilation packageCompilation) throws DataExplorerException {
        if (packageCompilation.diagnosticResult().hasErrors()) {
            StringJoiner errors = new StringJoiner(System.lineSeparator());
            errors.add("compilation error(s) found while creating executables for evaluation: ");
            packageCompilation.diagnosticResult().errors().forEach(error -> {
                if (error.diagnosticInfo().severity() == DiagnosticSeverity.ERROR) {
                    errors.add(error.message());
                }
            });
            throw new DataExplorerException(errors.toString());
        }
    }
}

