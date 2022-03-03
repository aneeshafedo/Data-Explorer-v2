package io.ballerina.dataexplorer;

import io.ballerina.dataexplorer.models.DBConfiguration;
import io.ballerina.dataexplorer.models.QueryParameter;
import org.eclipse.lsp4j.TextDocumentIdentifier;

import java.util.List;

public class DBQueryExecutionRequest {
    private DBConfiguration dbConfiguration;
    private List<QueryParameter> queryParameterList;
    private TextDocumentIdentifier ballerinaTomlFilePath;

    public DBQueryExecutionRequest(DBConfiguration dbConfiguration, List<QueryParameter> queryParameterList, TextDocumentIdentifier ballerinaTomlFilePath) {
        this.dbConfiguration = dbConfiguration;
        this.queryParameterList = queryParameterList;
        this.ballerinaTomlFilePath = ballerinaTomlFilePath;
    }

    public DBConfiguration getDbConfiguration() {
        return dbConfiguration;
    }

    public void setDbConfiguration(DBConfiguration dbConfiguration) {
        this.dbConfiguration = dbConfiguration;
    }

    public List<QueryParameter> getQueryParameterList() {
        return queryParameterList;
    }

    public void setQueryParameterList(List<QueryParameter> queryParameterList) {
        this.queryParameterList = queryParameterList;
    }

    public TextDocumentIdentifier getBallerinaTomlFilePath() {
        return ballerinaTomlFilePath;
    }

    public void setBallerinaTomlFilePath(TextDocumentIdentifier ballerinaTomlFilePath) {
        this.ballerinaTomlFilePath = ballerinaTomlFilePath;
    }
}
