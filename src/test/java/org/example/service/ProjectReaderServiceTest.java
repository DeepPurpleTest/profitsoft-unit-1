package org.example.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.model.Project;
import org.example.service.impl.ProjectReaderServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import util.DataUtils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
class ProjectReaderServiceTest {
	@InjectMocks
	ProjectReaderServiceImpl readerService;

	@Spy
	ObjectMapper objectMapper = new ObjectMapper();

	@Test
	void getStreamOfData_withRightPath_shouldReturnInputStream() throws FileNotFoundException {
		InputStream inputStream = readerService.getStreamOfData("src/test/resources/valid-test-data.json");

		assertNotNull(inputStream);
	}

	@Test
	void getStreamOfData_withWrongPath_shouldThrowFileNotFoundException() {
		assertThrows(FileNotFoundException.class, () -> readerService.getStreamOfData("src/test/resources/not-exist.json"));
	}

	@Test
	void parseJson_withValidData_shouldReturnValidList() throws IOException {
		// given
		List<Project> projects = objectMapper.readValue(DataUtils.getResourceAsStream("/valid-test-data.json"), new TypeReference<>() {
		});

		List<Project> projectList = readerService.parseJson(DataUtils.getResourceAsStream("/valid-test-data.json"));

		// then
		assertEquals(projects.size(), projectList.size());
		assertIterableEquals(projects, projectList);
	}

	@Test
	void parseJson_withInvalidData_shouldThrowIllegalStateException() {
		// given
		InputStream inputStream = DataUtils.getResourceAsStream("/invalid-test-data.json");

		// then
		assertThrows(IllegalStateException.class, () -> readerService.parseJson(inputStream));
	}
}
