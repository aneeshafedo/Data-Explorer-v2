package io.ballerina.dataexplorer.models;

import org.eclipse.lsp4j.Range;

/**
 *
 */
public class DataExplorerResponse {
    private Range lineRange;
    private String remoteCallExpression;
    private String remoteCallTypeDescriptor;
    private String remoteCallBindingPattern;
    private String initExpression;
    private String importStatement; //should be a string
    private String type;

    public DataExplorerResponse(Range lineRange, String remoteCallExpression, String remoteCallTypeDescriptor,
                                String remoteCallBindingPattern, String initExpression, String importStatement) {
        this.lineRange = lineRange;
        this.remoteCallExpression = remoteCallExpression;
        this.remoteCallTypeDescriptor = remoteCallTypeDescriptor;
        this.remoteCallBindingPattern = remoteCallBindingPattern;
        this.initExpression = initExpression;
        this.importStatement = importStatement;
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

    public String getRemoteCallTypeDescriptor() {
        return remoteCallTypeDescriptor;
    }

    public void setRemoteCallTypeDescriptor(String remoteCallTypeDescriptor) {
        this.remoteCallTypeDescriptor = remoteCallTypeDescriptor;
    }

    public String getRemoteCallBindingPattern() {
        return remoteCallBindingPattern;
    }

    public void setRemoteCallBindingPattern(String remoteCallBindingPattern) {
        this.remoteCallBindingPattern = remoteCallBindingPattern;
    }

    public String getInitExpression() {
        return initExpression;
    }

    public void setInitExpression(String initExpression) {
        this.initExpression = initExpression;
    }

    public String getImportStatement() {
        return importStatement;
    }

    public void setImportStatement(String importStatement) {
        this.importStatement = importStatement;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
