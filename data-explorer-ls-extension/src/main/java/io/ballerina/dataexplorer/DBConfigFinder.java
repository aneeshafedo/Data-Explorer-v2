package io.ballerina.dataexplorer;

import io.ballerina.compiler.syntax.tree.NodeVisitor;
import io.ballerina.compiler.syntax.tree.VariableDeclarationNode;

/**
 *
 */
public class DBConfigFinder extends NodeVisitor {
    DataExplorerRequest defaultDataExporerRequest = null;

    @Override
    public void visit(VariableDeclarationNode variableDeclarationNode) {
//        if (variableDeclarationNode.typedBindingPattern().typeDescriptor().toString().contains(DBQueryResult)) {
//
//        }
    }

}
