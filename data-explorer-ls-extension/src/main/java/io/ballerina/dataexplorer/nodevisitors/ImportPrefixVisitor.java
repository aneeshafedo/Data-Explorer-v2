package io.ballerina.dataexplorer.nodevisitors;

import io.ballerina.compiler.syntax.tree.NodeVisitor;
import io.ballerina.compiler.syntax.tree.QualifiedNameReferenceNode;

import java.util.ArrayList;
import java.util.List;

/**
 * This class visits the import prefixes.
 */
public class ImportPrefixVisitor extends NodeVisitor {
    private List<String> importPrefixes = new ArrayList<>();

    public List<String> getImportPrefixes() {
        return importPrefixes;
    }

    @Override
    public void visit(QualifiedNameReferenceNode qualifiedNameReferenceNode) {
        if (!qualifiedNameReferenceNode.modulePrefix().isMissing()) {
            importPrefixes.add(qualifiedNameReferenceNode.modulePrefix().text());
        }
    }
}
