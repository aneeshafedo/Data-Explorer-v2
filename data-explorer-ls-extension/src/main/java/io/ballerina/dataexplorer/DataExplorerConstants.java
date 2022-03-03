package io.ballerina.dataexplorer;

/**
 *
 */
public class DataExplorerConstants {
    public static final String CAPABILITY_NAME = "dataExplorerService";
    static final String SUCCESS = "Success";
    static final String DB_QUERY_RESULT = "stream<record {}, sql:Error?>";

    public enum DB_TYPES {
        MYSQL("mysql"),
        MSSQL("mssql"),
        ORACLE("oracle"),
        POSTGRESQL("postgresql");

        private final String dbType;

        DB_TYPES(String dbType) {
            this.dbType = dbType;
        }

        public String getValue() {
            return dbType;
        }
    }
}
