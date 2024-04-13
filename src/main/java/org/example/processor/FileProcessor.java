package org.example.processor;

import org.example.model.Project;

import java.io.File;
import java.nio.file.NoSuchFileException;
import java.util.List;

/**
 * Provides interface for working with files
 */
public interface FileProcessor {

	boolean isDirectoryPathValid(String directoryPath);

	List<File> getAllFilesByDirectoryPath(String directoryPath) throws NoSuchFileException;

	List<Project> extractDataFromFile(File file);
}
