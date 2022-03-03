package io.ballerina.dataexplorer;

public class DBQueryExexutionResponse {
    private String projectPath;

    public DBQueryExexutionResponse(String projectPath) {
        this.projectPath = projectPath;
    }

    public String getProjectPath() {
        return projectPath;
    }

    public void setProjectPath(String projectPath) {
        this.projectPath = projectPath;
    }
}
