package io.ballerina.dataexplorer.nodevisitors;

import io.ballerina.compiler.syntax.tree.IdentifierToken;
import io.ballerina.compiler.syntax.tree.ImportDeclarationNode;
import io.ballerina.compiler.syntax.tree.ImportPrefixNode;
import io.ballerina.compiler.syntax.tree.NodeVisitor;
import io.ballerina.compiler.syntax.tree.SeparatedNodeList;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * This class visits the import statements.
 */
public class ImportStatementVisitor extends NodeVisitor {
    private final List<String> dbConnectors = List.of("mysql", "mssql", "postgresql", "oracledb", "cdata.connect");
    private final List<String> importPrefixes;
    private List<String> importStatements = new ArrayList<>();

    public ImportStatementVisitor(List<String> importPrefixes) {
        this.importPrefixes = importPrefixes;
    }

    public List<String> getImportStatements() {
        return importStatements;
    }

    @Override
    public void visit(ImportDeclarationNode importDeclarationNode) {
        String detectedImportPrefix = "";
        Optional<ImportPrefixNode> optionalImportPrefixNode = importDeclarationNode.prefix();
        if (optionalImportPrefixNode.isPresent()) {
            ImportPrefixNode importPrefixNode = optionalImportPrefixNode.get();
            detectedImportPrefix = importPrefixNode.prefix().toString();
        } else {
            SeparatedNodeList<IdentifierToken> moduleNameNode = importDeclarationNode.moduleName();
            detectedImportPrefix = moduleNameNode.get(moduleNameNode.separatorSize()).toString();
        }
        if (this.importPrefixes.contains(detectedImportPrefix)) {
            this.importStatements.add(importDeclarationNode.toSourceCode().trim());
            if (this.dbConnectors.contains(detectedImportPrefix)) {
                this.importStatements.add("import ballerinax/" + detectedImportPrefix + ".driver as _;");
            }
        }
    }
}
