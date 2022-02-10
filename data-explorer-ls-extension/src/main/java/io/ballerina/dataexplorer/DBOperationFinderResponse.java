package io.ballerina.dataexplorer;

import org.eclipse.lsp4j.Range;

/**
 *
 */
public class DBOperationFinderResponse {

    private Range remoteCallPos;
    private String type;
    private String message;

    public DBOperationFinderResponse() {
    }

    public DBOperationFinderResponse(Range remoteCallPos, String type, String message) {
        this.remoteCallPos = remoteCallPos;
        this.type = type;
        this.message = message;
    }

    public Range getRemoteCallPos() {
        return remoteCallPos;
    }

    public void setRemoteCallPos(Range remoteCallPos) {
        this.remoteCallPos = remoteCallPos;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
