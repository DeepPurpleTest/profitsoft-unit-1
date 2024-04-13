package org.example.processor.impl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.model.Project;
import org.example.processor.FileProcessor;
import org.example.service.ReaderService;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

@Slf4j
@AllArgsConstructor
public class FileProcessorImpl implements FileProcessor {

	private final ReaderService readerService;

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

	public List<Project> extractDataFromFile(File file) {
		List<Project> projectList = new ArrayList<>();

		log.info("File path: " + file.getPath());
		try (InputStream is = readerService.getStreamOfData(file.getPath())) {
			log.info("Extract data from file: " + file.getName());
			projectList = readerService.parseJson(is);
		} catch (IOException e) {
			log.warn("Cannot extract data from file: {}. Exception message: {}",
					file.getName(), e.getMessage());
		}

		return projectList;
	}
}
