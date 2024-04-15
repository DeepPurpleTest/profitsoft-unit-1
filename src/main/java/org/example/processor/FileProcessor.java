package org.example.processor;

import java.io.File;
import java.nio.file.NoSuchFileException;
import java.util.List;

/**
 * Provides interface for working with files
 */
public interface FileProcessor<T> {

	boolean isDirectoryPathValid(String directoryPath);

	List<File> getAllFilesByDirectoryPath(String directoryPath) throws NoSuchFileException;

	List<T> extractDataFromFile(File file);
}
