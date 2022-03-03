package io.ballerina.dataexplorer;

import org.ballerinalang.langserver.commons.registration.BallerinaServerCapability;

/**
 *
 */
public class DataExplorerServerCapabilities extends BallerinaServerCapability {
    private boolean runDatabaseQuery;
    private boolean getRemoteFunctionCalls;

    public DataExplorerServerCapabilities() {
        super(DataExplorerConstants.CAPABILITY_NAME);
    }

    public boolean isRunDatabaseQuery() {
        return runDatabaseQuery;
    }

    public void setRunDatabaseQuery(boolean runDatabaseQuery) {
        this.runDatabaseQuery = runDatabaseQuery;
    }

    public boolean isGetRemoteFunctionCalls() {
        return getRemoteFunctionCalls;
    }

    public void setGetRemoteFunctionCalls(boolean getRemoteFunctionCalls) {
        this.getRemoteFunctionCalls = getRemoteFunctionCalls;
    }
}
