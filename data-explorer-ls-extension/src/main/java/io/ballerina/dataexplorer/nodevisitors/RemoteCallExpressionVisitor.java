package io.ballerina.dataexplorer.nodevisitors;

import io.ballerina.compiler.syntax.tree.NodeVisitor;
import io.ballerina.compiler.syntax.tree.VariableDeclarationNode;
import io.ballerina.dataexplorer.models.DataExplorerResponse;
import io.ballerina.tools.text.LineRange;
import org.eclipse.lsp4j.Position;
import org.eclipse.lsp4j.Range;

import java.util.ArrayList;
import java.util.List;

/**
 * This class visits the remote method call expressions.
 */
public class RemoteCallExpressionVisitor extends NodeVisitor {
    private List<DataExplorerResponse> dataExplorerResponses = new ArrayList<>();

    public List<DataExplorerResponse> getDataExplorerResponses() {
        return dataExplorerResponses;
    }

    @Override
    public void visit(VariableDeclarationNode variableDeclarationNode) {
        String remoteCallExpression = variableDeclarationNode.toSourceCode().trim();

        LineRange lineRange = variableDeclarationNode.lineRange();
        Range range = new Range(new Position(lineRange.startLine().line(), lineRange.startLine().offset()),
                new Position(lineRange.endLine().line(), lineRange.endLine().offset()));

        dataExplorerResponses.add(new DataExplorerResponse(range, remoteCallExpression));
    }
}
