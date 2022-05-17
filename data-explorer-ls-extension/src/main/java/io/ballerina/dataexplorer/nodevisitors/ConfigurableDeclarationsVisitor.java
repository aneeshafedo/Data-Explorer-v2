package io.ballerina.dataexplorer.nodevisitors;

import io.ballerina.compiler.syntax.tree.ModuleVariableDeclarationNode;
import io.ballerina.compiler.syntax.tree.NodeVisitor;

import java.util.ArrayList;
import java.util.List;

/**
 * This class visits the configurable variable declarations.
 */
public class ConfigurableDeclarationsVisitor extends NodeVisitor {
    private final List<String> arguments = new ArrayList<>();
    private List<String> configurableArguments = new ArrayList<>();
    private List<String> configurableDeclarations = new ArrayList<>();

    public ConfigurableDeclarationsVisitor(List<String> arguments) {
        this.arguments.addAll(arguments);
    }

    public List<String> getConfigurableArguments() {
        return configurableArguments;
    }

    public List<String> getConfigurableDeclarations() {
        return configurableDeclarations;
    }

    @Override
    public void visit(ModuleVariableDeclarationNode moduleVariableDeclarationNode) {
        if (moduleVariableDeclarationNode.toSourceCode().contains("configurable")) {
            if (arguments.contains(moduleVariableDeclarationNode.typedBindingPattern()
                    .bindingPattern().toString().trim())) {
                configurableArguments.add(moduleVariableDeclarationNode.typedBindingPattern()
                        .bindingPattern().toSourceCode().trim());
                configurableDeclarations.add(moduleVariableDeclarationNode.toSourceCode().trim());
            }
        }
    }
}
