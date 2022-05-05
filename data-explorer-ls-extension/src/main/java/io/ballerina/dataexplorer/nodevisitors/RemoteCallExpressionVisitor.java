package io.ballerina.dataexplorer.nodevisitors;

import io.ballerina.compiler.syntax.tree.FunctionBodyBlockNode;
import io.ballerina.compiler.syntax.tree.FunctionDefinitionNode;
import io.ballerina.compiler.syntax.tree.ModulePartNode;
import io.ballerina.compiler.syntax.tree.NodeVisitor;
import io.ballerina.compiler.syntax.tree.NonTerminalNode;
import io.ballerina.compiler.syntax.tree.VariableDeclarationNode;
import io.ballerina.dataexplorer.models.DataExplorerResponse;
import io.ballerina.tools.text.LineRange;
import org.eclipse.lsp4j.Position;
import org.eclipse.lsp4j.Range;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class RemoteCallExpressionVisitor extends NodeVisitor {
    private final String endpointName;
    List<DataExplorerResponse> dataExplorerResponses = new ArrayList<>();

    public RemoteCallExpressionVisitor(String endpointName) {
        this.endpointName = endpointName;
    }

    public List<DataExplorerResponse> getDataExplorerResponses() {
        return dataExplorerResponses;
    }

    public void setDataExplorerResponses(List<DataExplorerResponse> dataExplorerResponses) {
        this.dataExplorerResponses = dataExplorerResponses;
    }

    @Override
    public void visit(VariableDeclarationNode variableDeclarationNode) {
        String remoteCallExpression = variableDeclarationNode.toString().trim();
        String typeDescriptor = variableDeclarationNode.typedBindingPattern().typeDescriptor().toString().trim();
        String bindingPattern = variableDeclarationNode.typedBindingPattern().bindingPattern().toString().trim();

        String initExpression = "";
        String importStatement = "";
        NonTerminalNode blockNode = variableDeclarationNode.parent();
        if (blockNode instanceof FunctionBodyBlockNode) {
            InitExpressionVisitor initExpressionVisitor = new InitExpressionVisitor(endpointName);
            blockNode.accept(initExpressionVisitor);
            initExpression = initExpressionVisitor.getInitExpression();

            NonTerminalNode functionBodyBlockParentNode = blockNode.parent();
            if (functionBodyBlockParentNode instanceof FunctionDefinitionNode) {
                NonTerminalNode functionDefinitionParentNode = functionBodyBlockParentNode.parent();
                if (functionDefinitionParentNode instanceof ModulePartNode) {
                    String importPrefix = initExpression.split(":")[0].strip();
                    ImportStatementVisitor importStatementVisitor = new ImportStatementVisitor(importPrefix);
                    functionDefinitionParentNode.accept(importStatementVisitor);
                    importStatement = importStatementVisitor.getImportStatement();
                }
            }
        }
        LineRange lineRange = variableDeclarationNode.lineRange();
        Range range = new Range(new Position(lineRange.startLine().line(), lineRange.startLine().offset()),
                new Position(lineRange.endLine().line(), lineRange.endLine().offset()));

        dataExplorerResponses.add(new DataExplorerResponse(range, remoteCallExpression, typeDescriptor, bindingPattern,
                initExpression, importStatement));
    }
}
