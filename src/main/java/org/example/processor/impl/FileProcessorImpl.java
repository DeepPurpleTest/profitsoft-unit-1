package org.example.processor;

import lombok.Data;

import java.io.File;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

@Data
public class FileProcessor {

	public List<File> getAllFilesByDirectoryPath(String directoryPath) {
		return Stream.of(Objects.requireNonNull(new File(directoryPath).listFiles()))
				.filter(file -> !file.isDirectory())
				.toList();
	}
}
