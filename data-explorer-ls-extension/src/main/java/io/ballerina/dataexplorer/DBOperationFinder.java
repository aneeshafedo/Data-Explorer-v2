package io.ballerina.dataexplorer;

import io.ballerina.compiler.syntax.tree.NodeVisitor;
import io.ballerina.compiler.syntax.tree.RemoteMethodCallActionNode;
import io.ballerina.compiler.syntax.tree.SyntaxKind;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class DBOperationFinder extends NodeVisitor {
    List<CodeLensePos> connectors = new ArrayList<>();

    public List<CodeLensePos> getConnectors() {
        return connectors;
    }

    @Override
    public void visit(RemoteMethodCallActionNode remoteMethodCallActionNode) {

        if (remoteMethodCallActionNode.kind() == SyntaxKind.REMOTE_METHOD_CALL_ACTION &&
                remoteMethodCallActionNode.methodName().toString().equalsIgnoreCase("query")) {
            connectors.add(new CodeLensePos(remoteMethodCallActionNode.lineRange()));
        }
    }
}
