package org.example.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.model.Project;
import org.example.service.impl.ReaderServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static util.Data.getInvalidJsonData;
import static util.Data.getValidJsonData;

@ExtendWith(MockitoExtension.class)
class ReaderServiceTest {
	@InjectMocks
	ReaderServiceImpl readerService;

	@Spy
	ObjectMapper objectMapper = new ObjectMapper();

	@Test
	void parseJson_withValidData_shouldReturnValidList() throws IOException {
		// given
		List<Project> projects = objectMapper.readValue(getValidJsonData(), new TypeReference<>() {});
		InputStream inputStream = new ByteArrayInputStream(getValidJsonData().getBytes());

		List<Project> projectList = readerService.parseJson(inputStream);

		// then
		assertEquals(projects.size(), projectList.size());
		assertIterableEquals(projects, projectList);
	}

	@Test
	void parseJson_withInvalidData_shouldThrowIllegalStateException() {
		// given
		InputStream inputStream = new ByteArrayInputStream(getInvalidJsonData().getBytes());

		// then
		assertThrows(IllegalStateException.class, () -> readerService.parseJson(inputStream));
	}
}
