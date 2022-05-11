package io.ballerina.dataexplorer.nodevisitors;

import io.ballerina.compiler.syntax.tree.NodeVisitor;
import io.ballerina.compiler.syntax.tree.VariableDeclarationNode;

import java.util.ArrayList;
import java.util.List;

/**
 * This class visits the remote method endpoint initialization expression.
 */
public class InitExpressionVisitor extends NodeVisitor {
    private final String endpointName;
    private String initExpression = "";
    private List<String> arguments = new ArrayList<>();
    private List<String> importPrefixes = new ArrayList<>();

    public InitExpressionVisitor(String endpointName) {
        this.endpointName = endpointName;
    }

    public String getInitExpression() {
        return initExpression;
    }

    public List<String> getArguments() {
        return arguments;
    }

    public List<String> getImportPrefixes() {
        return importPrefixes;
    }

    @Override
    public void visit(VariableDeclarationNode variableDeclarationNode) {
        String bindingPattern = variableDeclarationNode.typedBindingPattern().bindingPattern().toSourceCode().trim();
        if (bindingPattern.equals(endpointName)) {
            this.initExpression = variableDeclarationNode.toSourceCode().trim();

            ArgumentsVisitor argumentsVisitor = new ArgumentsVisitor();
            variableDeclarationNode.accept(argumentsVisitor);
            arguments.addAll(argumentsVisitor.getArguments());

            ImportPrefixVisitor importPrefixVisitor = new ImportPrefixVisitor();
            variableDeclarationNode.accept(importPrefixVisitor);
            importPrefixes.addAll(importPrefixVisitor.getImportPrefixes());
        }
    }
}
