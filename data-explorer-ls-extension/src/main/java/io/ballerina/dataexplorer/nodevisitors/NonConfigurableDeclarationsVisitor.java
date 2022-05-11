package io.ballerina.dataexplorer.nodevisitors;

import io.ballerina.compiler.syntax.tree.BindingPatternNode;
import io.ballerina.compiler.syntax.tree.CaptureBindingPatternNode;
import io.ballerina.compiler.syntax.tree.NodeVisitor;
import io.ballerina.compiler.syntax.tree.VariableDeclarationNode;

import java.util.ArrayList;
import java.util.List;

/**
 * This class visits the non-configurable variable declarations.
 */
public class NonConfigurableDeclarationsVisitor extends NodeVisitor {
    private final List<String> nonConfigurableArguments = new ArrayList<>();
    private List<String> nonConfigurableDeclarations = new ArrayList<>();

    public NonConfigurableDeclarationsVisitor(List<String> arguments, List<String> configurableArguments) {
        List<String> filteredArguments = new ArrayList<>();
        filteredArguments.addAll(arguments);
        filteredArguments.removeAll(configurableArguments);
        this.nonConfigurableArguments.addAll(filteredArguments);
    }

    public List<String> getNonConfigurableDeclarations() {
        return nonConfigurableDeclarations;
    }

    @Override
    public void visit(VariableDeclarationNode variableDeclarationNode) {
        BindingPatternNode bindingPatternNode = variableDeclarationNode.typedBindingPattern().bindingPattern();
        if (bindingPatternNode instanceof CaptureBindingPatternNode) {
            if (nonConfigurableArguments.contains(
                    ((CaptureBindingPatternNode) bindingPatternNode).variableName().text().trim())) {
                nonConfigurableDeclarations.add(variableDeclarationNode.typedBindingPattern().toSourceCode().trim());
            }
        }
    }
}
