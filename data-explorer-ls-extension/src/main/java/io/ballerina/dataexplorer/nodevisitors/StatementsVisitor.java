package io.ballerina.dataexplorer.nodevisitors;

import io.ballerina.compiler.syntax.tree.NodeVisitor;
import io.ballerina.compiler.syntax.tree.SyntaxTree;
import io.ballerina.compiler.syntax.tree.VariableDeclarationNode;
import io.ballerina.dataexplorer.models.StatementsResponse;
import io.ballerina.projects.Document;
import io.ballerina.tools.text.LineRange;
import org.eclipse.lsp4j.Position;
import org.eclipse.lsp4j.Range;

import java.util.ArrayList;
import java.util.List;

/**
 * This class visits the remote method call expression to access the corresponding statements.
 */
public class StatementsVisitor extends NodeVisitor {
    private final List<String> dbConnectors = List.of("mysql", "mssql", "postgresql", "oracledb", "cdata.connect");
    private final Document document;
    private final String endpointName;
    private List<StatementsResponse> dataExplorerResponses = new ArrayList<>();

    public StatementsVisitor(Document document, String endpointName) {
        this.document = document;
        this.endpointName = endpointName;
    }

    public List<StatementsResponse> getDataExplorerResponses() {
        return dataExplorerResponses;
    }

    @Override
    public void visit(VariableDeclarationNode variableDeclarationNode) {
        String remoteCallExpression = variableDeclarationNode.toSourceCode().trim();
        String typeDescriptor = variableDeclarationNode.typedBindingPattern().typeDescriptor().toSourceCode().trim();
        String bindingPattern = variableDeclarationNode.typedBindingPattern().bindingPattern().toSourceCode().trim();

        String initExpression = "";
        List<String> importStatements = new ArrayList<>();
        List<String> configurableDeclarations = new ArrayList<>();
        List<String> nonConfigurableDeclarations = new ArrayList<>();
        boolean isDatabase = false;

        List<String> arguments = new ArrayList<>();
        List<String> importPrefixes = new ArrayList<>();

        ArgumentsVisitor argumentsVisitor = new ArgumentsVisitor();
        variableDeclarationNode.accept(argumentsVisitor);
        arguments.addAll(argumentsVisitor.getArguments());

        // Find Initialization Expression
        InitExpressionVisitor initExpressionVisitor = new InitExpressionVisitor(this.endpointName);
        SyntaxTree syntaxTree = this.document.syntaxTree();
        syntaxTree.rootNode().accept(initExpressionVisitor);
        initExpression = initExpressionVisitor.getInitExpression();
        arguments.addAll(initExpressionVisitor.getArguments());

        importPrefixes.addAll(initExpressionVisitor.getImportPrefixes());

        ImportPrefixVisitor importPrefixVisitor = new ImportPrefixVisitor();
        variableDeclarationNode.accept(importPrefixVisitor);
        importPrefixes.addAll(importPrefixVisitor.getImportPrefixes());

        // Find Import Statements
        ImportStatementVisitor importStatementVisitor = new ImportStatementVisitor(importPrefixes);
        syntaxTree.rootNode().accept(importStatementVisitor);
        importStatements.addAll(importStatementVisitor.getImportStatements());

        // Find Configurable Declarations
        ConfigurableDeclarationsVisitor configurableDeclarationsVisitor =
                new ConfigurableDeclarationsVisitor(arguments);
        syntaxTree.rootNode().accept(configurableDeclarationsVisitor);
        configurableDeclarations.addAll(configurableDeclarationsVisitor.getConfigurableDeclarations());

        // Find Non-Configurable Declarations
        NonConfigurableDeclarationsVisitor nonConfigurableDeclarationsVisitor =
                new NonConfigurableDeclarationsVisitor(arguments,
                        configurableDeclarationsVisitor.getConfigurableArguments());
        syntaxTree.rootNode().accept(nonConfigurableDeclarationsVisitor);
        nonConfigurableDeclarations.addAll(nonConfigurableDeclarationsVisitor.getNonConfigurableDeclarations());

        // Find isDatabase
        for (String importPrefix : importPrefixes) {
            if (dbConnectors.contains(importPrefix)) {
                isDatabase = true;
                break;
            }
        }

        LineRange lineRange = variableDeclarationNode.lineRange();
        Range range = new Range(new Position(lineRange.startLine().line(), lineRange.startLine().offset()),
                new Position(lineRange.endLine().line(), lineRange.endLine().offset()));

        dataExplorerResponses.add(new StatementsResponse(range, remoteCallExpression, typeDescriptor, bindingPattern,
                initExpression, importStatements, configurableDeclarations, nonConfigurableDeclarations, isDatabase));
    }
}
