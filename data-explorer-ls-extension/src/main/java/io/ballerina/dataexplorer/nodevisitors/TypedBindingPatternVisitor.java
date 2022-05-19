package io.ballerina.dataexplorer.nodevisitors;

import io.ballerina.compiler.syntax.tree.NodeVisitor;
import io.ballerina.compiler.syntax.tree.TypedBindingPatternNode;

import java.util.ArrayList;
import java.util.List;

/**
 * This class visits the type binding pattern.
 */
public class TypedBindingPatternVisitor extends NodeVisitor {
    private List<String> definedTypeNames = new ArrayList<>();

    public List<String> getDefinedTypeNames() {
        return definedTypeNames;
    }

    @Override
    public void visit(TypedBindingPatternNode typedBindingPatternNode) {
        TypeDescriptorVisitor typeDescriptorVisitor = new TypeDescriptorVisitor();
        typedBindingPatternNode.accept(typeDescriptorVisitor);
        definedTypeNames.addAll(typeDescriptorVisitor.getDefinedTypeNames());
    }
}
