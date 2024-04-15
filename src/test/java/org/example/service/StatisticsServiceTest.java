package org.example.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.model.Project;
import org.example.model.xml.Statistics;
import org.example.service.impl.StatisticsServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import util.DataUtils;

import java.io.IOException;
import java.util.Queue;

import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static util.DataUtils.getValidStatistics;

@ExtendWith(MockitoExtension.class)
class StatisticsServiceTest {
	private final StatisticsServiceImpl statisticsService = new StatisticsServiceImpl();

	private ObjectMapper objectMapper;

	@BeforeEach
	void setUp() {
		objectMapper = new ObjectMapper();
	}

	@Test
	void createStatistics_withExistsAttribute_shouldReturnValidStatistics() throws IOException {
		// given
		Queue<Project> projects = objectMapper.readValue(DataUtils.getResourceAsStream("/valid-test-data.json"),
				new TypeReference<>() {});

		String attribute = "members";

		Statistics statistics = statisticsService.createStatistics(projects, attribute);

		// then
		assertIterableEquals(getValidStatistics().getItems(), statistics.getItems());
	}
}
