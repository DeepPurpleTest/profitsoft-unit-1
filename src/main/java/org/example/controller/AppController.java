package org.example.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.model.Project;
import org.example.model.xml.Statistics;
import org.example.processor.FileProcessor;
import org.example.service.StatisticsService;
import org.example.service.XmlService;
import org.example.util.ReflectionUtils;

import java.io.File;
import java.nio.file.NoSuchFileException;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Main app controller which starts processing data
 */
@Slf4j
@AllArgsConstructor
public class AppController {

	private final StatisticsService statisticsService;
	private final XmlService xmlService;
	private final FileProcessor fileProcessor;

	public void processData(String directoryPath, String attribute) throws NoSuchFileException, NoSuchFieldException {
		beforeDataProcessing(directoryPath, attribute);

		String fieldName = ReflectionUtils.getFieldNameByAttribute(attribute, Project.class);
		List<File> files = fileProcessor.getAllFilesByDirectoryPath(directoryPath);
		LinkedBlockingQueue<Project> queue = new LinkedBlockingQueue<>();

		ExecutorService executorService = Executors.newFixedThreadPool(2);

		List<CompletableFuture<Void>> futures = createFutures(files, queue, executorService);

		CompletableFuture.allOf(futures.toArray(new CompletableFuture[0]))
				.thenRunAsync(() -> createAndWriteStatistics(queue, fieldName))
				.thenRun(executorService::shutdown);
	}

	private void beforeDataProcessing(String directoryPath, String attribute) throws NoSuchFileException, NoSuchFieldException {
		if(!fileProcessor.isDirectoryPathValid(directoryPath)) {
			throw new NoSuchFileException(directoryPath);
		}

		if (!ReflectionUtils.isAttributeExists(attribute, Project.class)) {
			throw new NoSuchFieldException(attribute);
		}
	}

	private List<CompletableFuture<Void>> createFutures(List<File> files, Queue<Project> projectList,
														ExecutorService executorService) {
		return files.stream()
				.map(file -> CompletableFuture.runAsync(() ->
						projectList.addAll(fileProcessor.extractDataFromFile(file)), executorService))
				.toList();
	}

	private void createAndWriteStatistics(Queue<Project> projectList, String fieldName) {
		Statistics statistics = statisticsService.createStatistics(projectList, fieldName);
		xmlService.writeInFile(statistics, fieldName);
	}
}
