package io.ballerina.dataexplorer;

import org.eclipse.lsp4j.TextDocumentIdentifier;

/**
 *
 */
public class DBOperationFinderRequest {
    private TextDocumentIdentifier documentIdentifier;

    public TextDocumentIdentifier getDocumentIdentifier() {

        return documentIdentifier;
    }

    public void setDocumentIdentifier(TextDocumentIdentifier documentIdentifier) {

        this.documentIdentifier = documentIdentifier;
    }
}
