package io.ballerina.dataexplorer;

import org.eclipse.lsp4j.TextDocumentIdentifier;

/**
 * Model class representing the data explorer request.
 */
public class DataExplorerRequest {
    private TextDocumentIdentifier documentIdentifier;

    public TextDocumentIdentifier getDocumentIdentifier() {

        return documentIdentifier;
    }

    public void setDocumentIdentifier(TextDocumentIdentifier documentIdentifier) {

        this.documentIdentifier = documentIdentifier;
    }
}
