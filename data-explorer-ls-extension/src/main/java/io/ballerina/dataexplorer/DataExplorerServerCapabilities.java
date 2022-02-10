package io.ballerina.dataexplorer;

import org.ballerinalang.langserver.commons.registration.BallerinaServerCapability;

/**
 *
 */
public class DataExplorerServerCapabilities extends BallerinaServerCapability {
    private boolean getResults;
    private boolean getRemoteFunctionCalls;

    public DataExplorerServerCapabilities() {
        super(DataExplorerConstants.CAPABILITY_NAME);
    }

    public boolean isGetResults() {
        return getResults;
    }

    public void setGetResults(boolean getResults) {
        this.getResults = getResults;
    }

    public boolean isGetRemoteFunctionCalls() {
        return getRemoteFunctionCalls;
    }

    public void setGetRemoteFunctionCalls(boolean getRemoteFunctionCalls) {
        this.getRemoteFunctionCalls = getRemoteFunctionCalls;
    }
}
