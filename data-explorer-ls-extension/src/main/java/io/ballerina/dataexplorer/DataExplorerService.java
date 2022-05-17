package io.ballerina.dataexplorer;

import io.ballerina.compiler.api.SemanticModel;
import io.ballerina.compiler.syntax.tree.SyntaxTree;
import io.ballerina.dataexplorer.models.DataExplorerResponse;
import io.ballerina.dataexplorer.models.StatementsResponse;
import io.ballerina.dataexplorer.nodevisitors.RemoteCallFinder;
import io.ballerina.dataexplorer.nodevisitors.StatementsFinder;
import io.ballerina.projects.Document;
import io.ballerina.projects.Module;
import org.ballerinalang.annotation.JavaSPIService;
import org.ballerinalang.langserver.commons.service.spi.ExtendedLanguageServerService;
import org.ballerinalang.langserver.commons.workspace.WorkspaceManager;
import org.eclipse.lsp4j.jsonrpc.services.JsonNotification;
import org.eclipse.lsp4j.jsonrpc.services.JsonSegment;
import org.eclipse.lsp4j.services.LanguageServer;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

import static io.ballerina.dataexplorer.DataExplorerConstants.SUCCESS;

/**
 * The extended service for the data explorer.
 */
@JavaSPIService("org.ballerinalang.langserver.commons.service.spi.ExtendedLanguageServerService")
@JsonSegment("dataExplorerService")
public class DataExplorerService implements ExtendedLanguageServerService {

    private WorkspaceManager workspaceManager;

    @Override
    public void init(LanguageServer langServer, WorkspaceManager workspaceManager) {
        this.workspaceManager = workspaceManager;
    }

    @Override
    public Class<?> getRemoteInterface() {
        return getClass();
    }

    @JsonNotification
    public  CompletableFuture<List<DataExplorerResponse>> getRemoteFunctionCalls(DataExplorerRequest request) {
        return CompletableFuture.supplyAsync(() -> {
            List<DataExplorerResponse> finalDataExplorerResponses = new ArrayList<>();
            String fileUri = request.getDocumentIdentifier().getUri();
            Path path = Path.of(fileUri);
            Optional<SemanticModel> semanticModel = this.workspaceManager.semanticModel(path);

            Optional<Module> module = this.workspaceManager.module(path);
            if (semanticModel.isEmpty() || module.isEmpty()) {
                return finalDataExplorerResponses;
            }

            Optional<Document> document = this.workspaceManager.document(path);
            if (document.isEmpty()) {
                return finalDataExplorerResponses;
            }

            RemoteCallFinder remoteCallFinder = new RemoteCallFinder();
            SyntaxTree syntaxTree = document.get().syntaxTree();
            syntaxTree.rootNode().accept(remoteCallFinder);

            List<DataExplorerResponse> dataExplorerResponses = remoteCallFinder.getDataExplorerResponses();
            for (DataExplorerResponse dataExplorerResponse : dataExplorerResponses) {
                dataExplorerResponse.setType(SUCCESS);
                finalDataExplorerResponses.add(dataExplorerResponse);
            }

            return finalDataExplorerResponses;
        });
    }

    @JsonNotification
    public  CompletableFuture<List<StatementsResponse>> getStatements(StatementsRequest request) {
        return CompletableFuture.supplyAsync(() -> {
            List<StatementsResponse> finalStatementsResponses = new ArrayList<>();
            String fileUri = request.getDocumentIdentifier().getUri();
            String remoteCallExpression = request.getRemoteCallExpression();
            Path path = Path.of(fileUri);
            Optional<SemanticModel> semanticModel = this.workspaceManager.semanticModel(path);

            Optional<Module> module = this.workspaceManager.module(path);
            if (semanticModel.isEmpty() || module.isEmpty()) {
                return finalStatementsResponses;
            }

            Optional<Document> document = this.workspaceManager.document(path);
            if (document.isEmpty()) {
                return finalStatementsResponses;
            }

            StatementsFinder statementsFinder = new StatementsFinder(document.get(), remoteCallExpression);
            SyntaxTree syntaxTree = document.get().syntaxTree();
            syntaxTree.rootNode().accept(statementsFinder);

            List<StatementsResponse> statementsResponses = statementsFinder.getStatementsResponses();
            for (StatementsResponse statementsResponse : statementsResponses) {
                statementsResponse.setType(SUCCESS);
                finalStatementsResponses.add(statementsResponse);
            }

            return finalStatementsResponses;
        });
    }
}

