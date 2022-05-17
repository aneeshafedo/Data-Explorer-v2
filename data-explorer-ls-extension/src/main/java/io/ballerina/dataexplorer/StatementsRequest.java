package io.ballerina.dataexplorer;

import org.eclipse.lsp4j.TextDocumentIdentifier;

/**
 * Model class representing the data explorer statements request.
 */
public class StatementsRequest {
    private TextDocumentIdentifier documentIdentifier;
    private String remoteCallExpression;

    public TextDocumentIdentifier getDocumentIdentifier() {
        return documentIdentifier;
    }

    public void setDocumentIdentifier(TextDocumentIdentifier documentIdentifier) {
        this.documentIdentifier = documentIdentifier;
    }

    public String getRemoteCallExpression() {
        return remoteCallExpression;
    }

    public void setRemoteCallExpression(String remoteCallExpression) {
        this.remoteCallExpression = remoteCallExpression;
    }
}
