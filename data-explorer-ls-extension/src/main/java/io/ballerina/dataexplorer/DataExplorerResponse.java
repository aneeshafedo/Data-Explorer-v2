package io.ballerina.dataexplorer;

import org.json.JSONArray;

/**
 *
 */
public class DataExplorerResponse {
    JSONArray resultStream;

    public DataExplorerResponse(JSONArray resultStream) {
        this.resultStream = resultStream;
    }

    public JSONArray getResultStream() {
        return resultStream;
    }

    public void setResultStream(JSONArray resultStream) {
        this.resultStream = resultStream;
    }
}
