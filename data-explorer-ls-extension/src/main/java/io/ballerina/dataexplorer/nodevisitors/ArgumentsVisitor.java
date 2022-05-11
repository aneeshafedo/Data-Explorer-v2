package io.ballerina.dataexplorer.nodevisitors;

import io.ballerina.compiler.syntax.tree.NamedArgumentNode;
import io.ballerina.compiler.syntax.tree.NodeVisitor;
import io.ballerina.compiler.syntax.tree.PositionalArgumentNode;

import java.util.ArrayList;
import java.util.List;

/**
 * This class visits function arguments.
 */
public class ArgumentsVisitor extends NodeVisitor {
    private List<String> arguments = new ArrayList<>();

    public List<String> getArguments() {
        return arguments;
    }

    @Override
    public void visit(NamedArgumentNode namedArgumentNode) {
        IdentifierTokenVisitor identifierTokenVisitor = new IdentifierTokenVisitor();
        namedArgumentNode.expression().accept(identifierTokenVisitor);
        arguments.addAll(identifierTokenVisitor.getIdentifierTokens());
    }

    @Override
    public void visit(PositionalArgumentNode positionalArgumentNode) {
        IdentifierTokenVisitor identifierTokenVisitor = new IdentifierTokenVisitor();
        positionalArgumentNode.accept(identifierTokenVisitor);
        arguments.addAll(identifierTokenVisitor.getIdentifierTokens());
    }
}
