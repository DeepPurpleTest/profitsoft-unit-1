package org.example.processor.impl;

import lombok.Data;
import org.example.processor.FileProcessor;

import java.io.File;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

@Data
public class FileProcessorImpl implements FileProcessor {

	public List<File> getAllFilesByDirectoryPath(String directoryPath) {
		File directory = new File(directoryPath);
		return Stream.of(Objects.requireNonNull(directory.listFiles()))
				.filter(file -> !file.isDirectory())
				.toList();
	}

	public boolean isDirectoryPathValid(String directoryPath) {
		File directory = new File(directoryPath);
		return directory.exists() && directory.isDirectory();
	}
}
