package io.ballerina.dataexplorer.nodevisitors;

import io.ballerina.compiler.syntax.tree.NodeVisitor;
import io.ballerina.compiler.syntax.tree.VariableDeclarationNode;

/**
 *
 */
public class InitExpressionVisitor extends NodeVisitor {
    private final String endpointName;
    private String initExpression;
    public InitExpressionVisitor(String endpointName) {
        this.endpointName = endpointName;
    }

    public String getInitExpression() {
        return initExpression;
    }

    public void setInitExpression(String initExpression) {
        this.initExpression = initExpression;
    }

    @Override
    public void visit(VariableDeclarationNode variableDeclarationNode) {
        String bindingPattern = variableDeclarationNode.typedBindingPattern().bindingPattern().toString().trim();
        if (bindingPattern.equals(endpointName)) {
            this.initExpression = variableDeclarationNode.toSourceCode().trim();
        }
    }
}
