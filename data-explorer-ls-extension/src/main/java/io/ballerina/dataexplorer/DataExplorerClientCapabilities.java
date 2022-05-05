package io.ballerina.dataexplorer;

import org.ballerinalang.langserver.commons.registration.BallerinaClientCapability;

/**
 *
 */
public class DataExplorerClientCapabilities extends BallerinaClientCapability {
    private boolean getRemoteFunctionCalls;

    public DataExplorerClientCapabilities() {
        super(DataExplorerConstants.CAPABILITY_NAME);
    }

    public boolean isGetRemoteFunctionCalls() {
        return getRemoteFunctionCalls;
    }

    public void setGetRemoteFunctionCalls(boolean getRemoteFunctionCalls) {
        this.getRemoteFunctionCalls = getRemoteFunctionCalls;
    }
}
