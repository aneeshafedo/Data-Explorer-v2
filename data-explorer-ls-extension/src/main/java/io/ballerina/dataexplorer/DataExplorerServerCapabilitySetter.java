package io.ballerina.dataexplorer;

import org.ballerinalang.annotation.JavaSPIService;
import org.ballerinalang.langserver.commons.registration.BallerinaServerCapabilitySetter;

import java.util.Optional;

/**
 *
 */
@JavaSPIService("org.ballerinalang.langserver.commons.registration.BallerinaServerCapabilitySetter")
public class DataExplorerServerCapabilitySetter extends BallerinaServerCapabilitySetter
        <DataExplorerServerCapabilities> {

    @Override
    public Optional<DataExplorerServerCapabilities> build() {
        DataExplorerServerCapabilities capabilities = new DataExplorerServerCapabilities();
        capabilities.setRunDatabaseQuery(true);
        capabilities.setGetRemoteFunctionCalls(true);
        return Optional.of(capabilities);
    }

    @Override
    public String getCapabilityName() {
        return DataExplorerConstants.CAPABILITY_NAME;
    }

    @Override
    public Class<DataExplorerServerCapabilities> getCapability() {
        return DataExplorerServerCapabilities.class;
    }
}
