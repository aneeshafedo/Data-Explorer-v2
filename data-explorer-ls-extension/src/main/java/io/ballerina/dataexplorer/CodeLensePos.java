package io.ballerina.dataexplorer;

import io.ballerina.tools.text.LineRange;

/**
 *
 */
public class CodeLensePos {
    private LineRange lineRange;

    public CodeLensePos(LineRange lineRange) {
        this.lineRange = lineRange;
    }

    public LineRange getLineRange() {
        return lineRange;
    }

    public void setLineRange(LineRange lineRange) {
        this.lineRange = lineRange;
    }
}
