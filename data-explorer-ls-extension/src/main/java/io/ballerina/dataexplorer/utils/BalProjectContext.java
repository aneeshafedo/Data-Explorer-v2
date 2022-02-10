package io.ballerina.dataexplorer.utils;

import java.util.Collection;

/**
 *
 */
public class BalProjectContext {
    private Collection<String> importsList; //need to find out the relevant imports only
    private String params; // need to store paramName, type and value
    private Collection<String> variableList;
    private String queryString;

    private String clientType;

    public BalProjectContext(Collection<String> importsList, String params, Collection<String> variableList,
                             String queryString, String clientType) {
        this.importsList = importsList;
        this.params = params;
        this.variableList = variableList;
        this.queryString = queryString;
        this.clientType = clientType;
    }

    public Collection<String> getImportsList() {
        return importsList;
    }

    public void setImportsList(Collection<String> importsList) {
        this.importsList = importsList;
    }

    public String getParams() {
        return params;
    }

    public void setParams(String paramList) {
        this.params = params;
    }

    public Collection<String> getVariableList() {
        return variableList;
    }

    public void setVariableList(Collection<String> variableList) {
        this.variableList = variableList;
    }

    public String getQueryString() {
        return queryString;
    }

    public void setQueryString(String queryString) {
        this.queryString = queryString;
    }

    public String getClientType() {
        return clientType;
    }

    public void setClientType(String clientType) {
        this.clientType = clientType;
    }
}
