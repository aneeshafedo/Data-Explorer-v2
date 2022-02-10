package io.ballerina.dataexplorer;

import org.ballerinalang.annotation.JavaSPIService;
import org.ballerinalang.langserver.commons.registration.BallerinaClientCapabilitySetter;

/**
 *
 */
@JavaSPIService("org.ballerinalang.langserver.commons.registration.BallerinaClientCapabilitySetter")
public class DataExplorerClientCapabilitySetter extends BallerinaClientCapabilitySetter
        <DataExplorerClientCapabilities> {
    @Override
    public String getCapabilityName() {
        return DataExplorerConstants.CAPABILITY_NAME;
    }

    @Override
    public Class<DataExplorerClientCapabilities> getCapability() {
        return DataExplorerClientCapabilities.class;
    }
}
