package io.ballerina.dataexplorer;

/**
 *
 */
public class DataExplorerException extends Exception {
    public DataExplorerException(String message, Throwable e) {
        super(message, e);
    }

    public DataExplorerException(String message) {
        super(message);
    }
}
