package io.ballerina.dataexplorer.nodevisitors;

import io.ballerina.compiler.syntax.tree.NodeVisitor;
import io.ballerina.compiler.syntax.tree.SimpleNameReferenceNode;
import io.ballerina.compiler.syntax.tree.SyntaxKind;

import java.util.ArrayList;
import java.util.List;

/**
 * This class visits the stream type params.
 */
public class StreamTypeParamsVisitor extends NodeVisitor {
    private List<String> definedTypeNames = new ArrayList<>();

    public List<String> getDefinedTypeNames() {
        return definedTypeNames;
    }

    public void visit(SimpleNameReferenceNode simpleNameReferenceNode) {
        if (simpleNameReferenceNode.name().kind() == SyntaxKind.IDENTIFIER_TOKEN) {
            definedTypeNames.add(simpleNameReferenceNode.name().text());
        }
    }
}
