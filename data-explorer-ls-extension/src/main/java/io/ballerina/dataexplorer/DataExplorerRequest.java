package io.ballerina.dataexplorer;

import java.util.List;

/**
 * ..
 */
public class DataExplorerRequest {

    private List<String> importsList; //need to find out the relevant imports only
    private List<String> paramList; // need to store paramName, type and value
    private List<String> variableList;
    private String queryString;
    private String ballerinaTomlFilePath;

    private String clientType;

    public DataExplorerRequest(List<String> importsList, List<String> paramList, List<String> variableList,
                               String queryString, String ballerinaTomlFilePath, String clientType) {
        this.importsList = importsList;
        this.paramList = paramList;
        this.variableList = variableList;
        this.queryString = queryString;
        this.ballerinaTomlFilePath = ballerinaTomlFilePath;
        this.clientType = clientType;
    }

    public List<String> getImportsList() {
        return importsList;
    }

    public void setImportsList(List<String> importsList) {
        this.importsList = importsList;
    }

    public List<String> getParams() {
        return paramList;
    }

    public void setParams(List<String> paramMap) {
        this.paramList = paramMap;
    }

    public List<String> getVariables() {
        return variableList;
    }

    public void setVariables(List<String> variableMap) {
        this.variableList = variableMap;
    }

    public String getQueryString() {
        return queryString;
    }

    public void setQueryString(String queryString) {
        this.queryString = queryString;
    }

    public String getBallerinaTomlFilePath() {
        return ballerinaTomlFilePath;
    }

    public void setBallerinaTomlFilePath(String ballerinaTomlFilePath) {
        this.ballerinaTomlFilePath = ballerinaTomlFilePath;
    }

    public String getClientType() {
        return clientType;
    }

    public void setClientType(String clientType) {
        this.clientType = clientType;
    }

}

