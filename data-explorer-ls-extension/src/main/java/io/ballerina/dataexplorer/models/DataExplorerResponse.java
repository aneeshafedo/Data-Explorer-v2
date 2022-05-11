package io.ballerina.dataexplorer.models;

import org.eclipse.lsp4j.Range;

/**
 * Model class representing the data explorer response.
 */
public class DataExplorerResponse {
    private Range lineRange;
    private String remoteCallExpression;
    private String type;

    public DataExplorerResponse(Range lineRange, String remoteCallExpression) {
        this.lineRange = lineRange;
        this.remoteCallExpression = remoteCallExpression;
    }

    public Range getLineRange() {
        return lineRange;
    }

    public void setLineRange(Range lineRange) {
        this.lineRange = lineRange;
    }

    public String getRemoteCallExpression() {
        return remoteCallExpression;
    }

    public void setRemoteCallExpression(String remoteCallExpression) {
        this.remoteCallExpression = remoteCallExpression;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
