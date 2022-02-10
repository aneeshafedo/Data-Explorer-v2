package io.ballerina.dataexplorer.utils;

import io.ballerina.dataexplorer.DataExplorerException;

import java.io.File;
import java.io.FileWriter;
import java.nio.charset.Charset;

/**
 *
 */
public class DataExplorerUtils {

    /**
     * Helper method to write a string source to a file.
     *
     * @param content Content to write to the file.
     * @throws DataExplorerException If writing was unsuccessful.
     */
    public static void writeToFile(File file, String content) throws DataExplorerException {
        try (FileWriter fileWriter = new FileWriter(file, Charset.defaultCharset())) {
            fileWriter.write(content);
        } catch (Exception e) {
            throw new DataExplorerException(String.format("error occurred while writing to a temp file at: '%s'",
                    file.getPath()));
        }
    }
}
