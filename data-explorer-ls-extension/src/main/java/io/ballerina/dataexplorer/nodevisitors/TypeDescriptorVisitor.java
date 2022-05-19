package io.ballerina.dataexplorer.nodevisitors;

import io.ballerina.compiler.syntax.tree.Node;
import io.ballerina.compiler.syntax.tree.NodeVisitor;
import io.ballerina.compiler.syntax.tree.QualifiedNameReferenceNode;
import io.ballerina.compiler.syntax.tree.StreamTypeDescriptorNode;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * This class visits the type descriptor.
 */
public class TypeDescriptorVisitor extends NodeVisitor {
    private List<String> definedTypeNames = new ArrayList<>();

    public List<String> getDefinedTypeNames() {
        return definedTypeNames;
    }

    @Override
    public void visit(QualifiedNameReferenceNode qualifiedNameReferenceNode) {
        if (qualifiedNameReferenceNode.modulePrefix().isMissing()) {
            definedTypeNames.add(qualifiedNameReferenceNode.toSourceCode().trim());
        }
    }

    @Override
    public void visit(StreamTypeDescriptorNode streamTypeDescriptorNode) {
        Optional<Node> streamTypeParamsNode = streamTypeDescriptorNode.streamTypeParamsNode();
        if (streamTypeParamsNode.isPresent()) {
            StreamTypeParamsVisitor streamTypeParamsVisitor = new StreamTypeParamsVisitor();
            streamTypeParamsNode.get().accept(streamTypeParamsVisitor);
            definedTypeNames.addAll(streamTypeParamsVisitor.getDefinedTypeNames());
        }
    }
}
