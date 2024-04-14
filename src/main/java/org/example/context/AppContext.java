package org.example.context;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.controller.AppController;
import org.example.model.Project;
import org.example.processor.FileProcessor;
import org.example.processor.impl.FileProcessorImpl;
import org.example.service.ReaderService;
import org.example.service.StatisticsService;
import org.example.service.XmlService;
import org.example.service.impl.ProjectReaderServiceImpl;
import org.example.service.impl.StatisticsServiceImpl;
import org.example.service.impl.XmlServiceImpl;

/**
 * Class represents an application context with the creation of objects to be used in it
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AppContext {
	private static final AppContext appContext = new AppContext();

	private final ObjectMapper objectMapper = objectMapper();

	private final XmlMapper xmlMapper = xmlMapper();

	@Getter
	private final XmlService xmlService = new XmlServiceImpl(xmlMapper);

	@Getter
	private final ReaderService<Project> readerService = new ProjectReaderServiceImpl(objectMapper);

	@Getter
	private final FileProcessor fileProcessor = new FileProcessorImpl(readerService);

	@Getter
	private final StatisticsService statisticsService = new StatisticsServiceImpl();

	@Getter
	private final AppController appController = new AppController(statisticsService,
			xmlService, fileProcessor);


	public static AppContext getInstance() {
		return appContext;
	}

	private XmlMapper xmlMapper() {
		return (XmlMapper) new XmlMapper().enable(SerializationFeature.INDENT_OUTPUT);
	}

	private ObjectMapper objectMapper() {
		ObjectMapper objectMapperToConfig = new ObjectMapper();
		objectMapperToConfig.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		objectMapperToConfig.registerModule(new JavaTimeModule());
		return objectMapperToConfig;
	}
}
