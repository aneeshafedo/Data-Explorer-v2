package io.ballerina.dataexplorer.models;

import io.ballerina.dataexplorer.DataExplorerConstants;

public class DBConfiguration {
//    private DataExplorerConstants.DB_TYPES dbType;
    private String dbType;
    private String host;
    private String user;
    private String password;
    private int port;
    private String database;
    private String query;

    public DBConfiguration(String databaseType, String host, String user, String password, int port, String database, String query) {
        this.dbType = databaseType;
        this.host = host;
        this.user = user;
        this.password = password;
        this.port = port;
        this.database = database;
        this.query = query;
    }

    public String getDbType() {
        return dbType;
    }

    public void setDbType(String dbType) {
        this.dbType = dbType;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDatabase() {
        return database;
    }

    public void setDatabase(String database) {
        this.database = database;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }
}
