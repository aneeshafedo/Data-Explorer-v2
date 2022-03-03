package io.ballerina.dataexplorer;

import io.ballerina.compiler.syntax.tree.NodeVisitor;
import io.ballerina.compiler.syntax.tree.VariableDeclarationNode;

import java.util.ArrayList;
import java.util.List;

import static io.ballerina.dataexplorer.DataExplorerConstants.DB_QUERY_RESULT;

/**
 *
 */
public class DBOperationFinder extends NodeVisitor {
    List<CodeLensePos> connectors = new ArrayList<>();

    public List<CodeLensePos> getConnectors() {
        return connectors;
    }

    @Override
    public void visit(VariableDeclarationNode variableDeclarationNode) {
        if (variableDeclarationNode.typedBindingPattern().typeDescriptor().toString().contains(DB_QUERY_RESULT)) {
            connectors.add(new CodeLensePos(variableDeclarationNode.lineRange()));
        }
    }

//    @Override
//    public void visit(RemoteMethodCallActionNode remoteMethodCallActionNode) {
//
//        if (remoteMethodCallActionNode.kind() == SyntaxKind.REMOTE_METHOD_CALL_ACTION &&
//                remoteMethodCallActionNode.methodName().toString().equalsIgnoreCase("query")) {
//            connectors.add(new CodeLensePos(remoteMethodCallActionNode.lineRange()));
//        }
//    }
}
