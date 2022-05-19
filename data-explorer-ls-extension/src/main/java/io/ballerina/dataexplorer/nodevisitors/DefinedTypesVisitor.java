package io.ballerina.dataexplorer.nodevisitors;

import io.ballerina.compiler.syntax.tree.NodeVisitor;
import io.ballerina.compiler.syntax.tree.TypeDefinitionNode;

import java.util.ArrayList;
import java.util.List;

/**
 * This class visits defined types.
 */
public class DefinedTypesVisitor extends NodeVisitor {
    private final List<String> definedTypeNames;
    private List<String> definedTypeDefinitions = new ArrayList<>();

    public DefinedTypesVisitor(List<String> definedTypeNames) {
        this.definedTypeNames = definedTypeNames;
    }

    public List<String> getDefinedTypeDefinitions() {
        return definedTypeDefinitions;
    }

    @Override
    public void visit(TypeDefinitionNode typeDefinitionNode) {
        if (definedTypeNames.contains(typeDefinitionNode.typeName().text())) {
            definedTypeDefinitions.add(typeDefinitionNode.toSourceCode().trim());
        }
    }
}
