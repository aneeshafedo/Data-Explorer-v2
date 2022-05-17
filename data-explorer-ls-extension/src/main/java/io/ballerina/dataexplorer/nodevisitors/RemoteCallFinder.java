package io.ballerina.dataexplorer.nodevisitors;

import io.ballerina.compiler.syntax.tree.CheckExpressionNode;
import io.ballerina.compiler.syntax.tree.NodeVisitor;
import io.ballerina.compiler.syntax.tree.NonTerminalNode;
import io.ballerina.compiler.syntax.tree.RemoteMethodCallActionNode;
import io.ballerina.compiler.syntax.tree.VariableDeclarationNode;
import io.ballerina.dataexplorer.models.DataExplorerResponse;

import java.util.ArrayList;
import java.util.List;

/**
 * This class visits the remote method call actions.
 */
public class RemoteCallFinder extends NodeVisitor {
    private List<DataExplorerResponse> dataExplorerResponses = new ArrayList<>();

    public List<DataExplorerResponse> getDataExplorerResponses() {
        return dataExplorerResponses;
    }

    @Override
    public void visit(RemoteMethodCallActionNode remoteMethodCallActionNode) {
        NonTerminalNode remoteMethodCallActionParentNode = remoteMethodCallActionNode.parent();
        if (remoteMethodCallActionParentNode instanceof VariableDeclarationNode) {
            RemoteCallExpressionVisitor remoteCallExpressionVisitor = new RemoteCallExpressionVisitor();
            remoteMethodCallActionParentNode.accept(remoteCallExpressionVisitor);
            dataExplorerResponses.addAll(remoteCallExpressionVisitor.getDataExplorerResponses());
        }
        if (remoteMethodCallActionParentNode instanceof CheckExpressionNode) {
            NonTerminalNode variableDeclarationNode = remoteMethodCallActionParentNode.parent();
            RemoteCallExpressionVisitor remoteCallExpressionVisitor = new RemoteCallExpressionVisitor();
            variableDeclarationNode.accept(remoteCallExpressionVisitor);
            dataExplorerResponses.addAll(remoteCallExpressionVisitor.getDataExplorerResponses());
        }
    }
}
