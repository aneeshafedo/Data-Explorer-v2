package io.ballerina.dataexplorer.nodevisitors;

import io.ballerina.compiler.syntax.tree.NodeVisitor;
import io.ballerina.compiler.syntax.tree.SimpleNameReferenceNode;
import io.ballerina.compiler.syntax.tree.SyntaxKind;

import java.util.ArrayList;
import java.util.List;

/**
 * This class visits the identifier tokens in expression/statements.
 */
public class IdentifierTokenVisitor extends NodeVisitor {
    private List<String> identifierTokens = new ArrayList<>();

    public List<String> getIdentifierTokens() {
        return identifierTokens;
    }

    @Override
    public void visit(SimpleNameReferenceNode simpleNameReferenceNode) {
        if (simpleNameReferenceNode.name().kind().equals(SyntaxKind.IDENTIFIER_TOKEN)) {
            identifierTokens.add(simpleNameReferenceNode.name().text());
        }
    }
}
