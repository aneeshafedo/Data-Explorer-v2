package io.ballerina.dataexplorer.models;

import org.eclipse.lsp4j.Range;

import java.util.List;

/**
 * Model class representing the data explorer statements response.
 */
public class StatementsResponse {
    private Range lineRange;
    private String remoteCallExpression;
    private String remoteCallTypeDescriptor;
    private String remoteCallBindingPattern;
    private String initExpression;
    private List<String> importStatements;
    private List<String> configurableDeclarations;
    private List<String> nonConfigurableDeclarations;
    private boolean isDatabase;
    private String type;

    public StatementsResponse(Range lineRange, String remoteCallExpression, String remoteCallTypeDescriptor,
                              String remoteCallBindingPattern, String initExpression, List<String> importStatements,
                              List<String> configurableDeclarations, List<String> nonConfigurableDeclarations,
                              boolean isDatabase) {
        this.lineRange = lineRange;
        this.remoteCallExpression = remoteCallExpression;
        this.remoteCallTypeDescriptor = remoteCallTypeDescriptor;
        this.remoteCallBindingPattern = remoteCallBindingPattern;
        this.initExpression = initExpression;
        this.importStatements = importStatements;
        this.configurableDeclarations = configurableDeclarations;
        this.nonConfigurableDeclarations = nonConfigurableDeclarations;
        this.isDatabase = isDatabase;
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

    public List<String> getImportStatements() {
        return importStatements;
    }

    public void setImportStatements(List<String> importStatements) {
        this.importStatements = importStatements;
    }

    public List<String> getConfigurableDeclarations() {
        return configurableDeclarations;
    }

    public void setConfigurableDeclarations(List<String> configurableDeclarations) {
        this.configurableDeclarations = configurableDeclarations;
    }

    public List<String> getNonConfigurableDeclarations() {
        return nonConfigurableDeclarations;
    }

    public void setNonConfigurableDeclarations(List<String> nonConfigurableDeclarations) {
        this.nonConfigurableDeclarations = nonConfigurableDeclarations;
    }

    public boolean isDatabase() {
        return isDatabase;
    }

    public void setDatabase(boolean database) {
        isDatabase = database;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
