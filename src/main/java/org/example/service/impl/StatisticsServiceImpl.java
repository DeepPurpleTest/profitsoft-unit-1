package org.example.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.example.model.Project;
import org.example.model.xml.Item;
import org.example.model.xml.Statistics;
import org.example.service.StatisticsService;
import org.example.util.ReflectionUtils;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Queue;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
public class StatisticsServiceImpl implements StatisticsService {

	public Statistics createStatistics(Queue<Project> projectList, String fieldName) {
		Map<String, Long> statisticsMap = projectList.stream()
				.map(project -> ReflectionUtils.getFieldValue(project, fieldName))
				.filter(Objects::nonNull)
				.flatMap(fieldValue -> cutFieldValue(String.valueOf(fieldValue)).stream())
				.collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

		List<Item> items = statisticsMap.entrySet().stream()
				.map(entry -> new Item(String.valueOf(entry.getKey()), entry.getValue().intValue()))
				.sorted(Comparator.comparingInt(Item::getCount).reversed())
				.toList();

		Statistics statistics = new Statistics();
		statistics.setItems(items);

		return statistics;
	}

	private List<String> cutFieldValue(String fieldValue) {
		return Arrays.stream(fieldValue.split(","))
				.map(String::trim)
				.toList();
	}
}
