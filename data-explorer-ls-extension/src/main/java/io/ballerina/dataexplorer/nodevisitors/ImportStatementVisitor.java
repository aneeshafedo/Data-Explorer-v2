package io.ballerina.dataexplorer.nodevisitors;

import io.ballerina.compiler.syntax.tree.IdentifierToken;
import io.ballerina.compiler.syntax.tree.ImportDeclarationNode;
import io.ballerina.compiler.syntax.tree.ImportPrefixNode;
import io.ballerina.compiler.syntax.tree.NodeVisitor;
import io.ballerina.compiler.syntax.tree.SeparatedNodeList;

import java.util.Optional;

/**
 *
 */
public class ImportStatementVisitor extends NodeVisitor {
    private final String importPrefix;
    private String importStatement;
    public ImportStatementVisitor(String importPrefix) {
        this.importPrefix = importPrefix;
    }

    public String getImportStatement() {
        return importStatement;
    }

    public void setImportStatement(String importStatement) {
        this.importStatement = importStatement;
    }

    @Override
    public void visit(ImportDeclarationNode importDeclarationNode) {
        String generatedImportPrefix;
        Optional<ImportPrefixNode> optionalImportPrefixNode = importDeclarationNode.prefix();
        if (optionalImportPrefixNode.isPresent()) {
            ImportPrefixNode importPrefixNode = optionalImportPrefixNode.get();
            generatedImportPrefix = importPrefixNode.prefix().toString();
        } else {
            SeparatedNodeList<IdentifierToken> moduleNameNode = importDeclarationNode.moduleName();
            generatedImportPrefix = moduleNameNode.get(moduleNameNode.separatorSize()).toString();
        }
        if (generatedImportPrefix.equals(this.importPrefix)) {
            this.importStatement = importDeclarationNode.toSourceCode();
        }
    }
}
