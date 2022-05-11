package io.ballerina.dataexplorer.nodevisitors;

import io.ballerina.compiler.syntax.tree.CheckExpressionNode;
import io.ballerina.compiler.syntax.tree.NodeVisitor;
import io.ballerina.compiler.syntax.tree.NonTerminalNode;
import io.ballerina.compiler.syntax.tree.RemoteMethodCallActionNode;
import io.ballerina.compiler.syntax.tree.VariableDeclarationNode;
import io.ballerina.dataexplorer.models.StatementsResponse;
import io.ballerina.projects.Document;

import java.util.ArrayList;
import java.util.List;

/**
 * This class visits the remote method call actions to access the corresponding statements.
 */
public class StatementsFinder extends NodeVisitor {
    private final Document document;
    private final String remoteCallExpression;
    private List<StatementsResponse> statementsResponses = new ArrayList<>();

    public StatementsFinder(Document document, String remoteCallExpression) {
        this.document = document;
        this.remoteCallExpression = remoteCallExpression;
    }

    public List<StatementsResponse> getStatementsResponses() {
        return statementsResponses;
    }

    @Override
    public void visit(RemoteMethodCallActionNode remoteMethodCallActionNode) {
        String sourceCode = remoteMethodCallActionNode.toSourceCode();
        String endpointName = sourceCode.split("->")[0];

        NonTerminalNode remoteMethodCallActionParentNode = remoteMethodCallActionNode.parent();
        if (remoteMethodCallActionParentNode instanceof VariableDeclarationNode) {
            if (this.remoteCallExpression.equals(remoteMethodCallActionParentNode.toSourceCode().trim())) {
                StatementsVisitor statementsVisitor = new StatementsVisitor(this.document, endpointName);
                remoteMethodCallActionParentNode.accept(statementsVisitor);
                statementsResponses.addAll(statementsVisitor.getDataExplorerResponses());
            }
        }
        if (remoteMethodCallActionParentNode instanceof CheckExpressionNode) {
            NonTerminalNode variableDeclarationNode = remoteMethodCallActionParentNode.parent();
            if (this.remoteCallExpression.equals(variableDeclarationNode.toSourceCode().trim())) {
                StatementsVisitor statementsVisitor = new StatementsVisitor(this.document, endpointName);
                variableDeclarationNode.accept(statementsVisitor);
                statementsResponses.addAll(statementsVisitor.getDataExplorerResponses());
            }
        }
    }
}
