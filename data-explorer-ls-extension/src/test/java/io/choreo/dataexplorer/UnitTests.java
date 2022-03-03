package io.choreo.dataexplorer;

import io.ballerina.dataexplorer.DBQueryExecutionRequest;
import io.ballerina.dataexplorer.DataExplorerRequest;
import io.ballerina.dataexplorer.DataExplorerService;
import io.ballerina.dataexplorer.models.DBConfiguration;
import org.eclipse.lsp4j.TextDocumentIdentifier;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * Unit tests.
 */
public class UnitTests {

    @Test
    public void testGetMSSQLResults() throws Exception {
        DBConfiguration dbConfiguration = new DBConfiguration("mysql", "localhost", "root",
                "root", 3306, "TestDB", "SELECT * FROM students");
        TextDocumentIdentifier documentIdentifier = new TextDocumentIdentifier("/home/aneesha/Documents/Dev-Zone/" +
                "data-explorer/v5/data-explorer/data-explorer-ls-extension/src/test/resources/Ballerina.toml");
        DBQueryExecutionRequest request = new DBQueryExecutionRequest(dbConfiguration,
                new ArrayList<>(), documentIdentifier);
        DataExplorerService dataExplorerService = new DataExplorerService();
        dataExplorerService.runDatabaseQuery(request);
    }
}

//    @Test
//    public void testGetMySQLResults() throws Exception {
//        List<String> importsList = Arrays.asList("ballerinax/mysql", "ballerinax/mysql.driver as _",
//                "ballerina/sql", "ballerina/io");
//        List<String> paramMap = Arrays.asList(
//                "host = \"localhost\"",
//                "user = \"root\"",
//                "password = \"root\"",
//                "database = \"TestDB\"",
//                "port = 3306"
//        );
//        List<String> variableMap = List.of("string name = \"Aneesha\"");
//        String queryString = "SELECT * FROM students WHERE name = ${name}";
//        String ballerinaTomlFilePath =
//                "/home/aneesha/Documents/Dev-Zone/data-explorer/v5/data-explorer/data-explorer-ls-extension" +
//                        "/src/test/resources/Ballerina.toml";
//
//        DataExplorerRequest dataExplorerRequest = new DataExplorerRequest(importsList, paramMap, variableMap,
//                queryString, ballerinaTomlFilePath, "mysql:Client");
//
//        DataExplorerService dataExplorerService = new DataExplorerService();
//        dataExplorerService.getResults(dataExplorerRequest);
//    }
//}
