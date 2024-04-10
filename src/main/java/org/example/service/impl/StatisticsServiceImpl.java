package org.example.service;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.extern.slf4j.Slf4j;
import org.example.model.Project;
import org.example.model.xml.Item;
import org.example.model.xml.Statistics;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Queue;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
public class StatisticsService {

	public Statistics createStatistics(Queue<Project> projectList, String attribute) {
		Map<String, Long> statisticsMap = projectList.stream()
				.map(project -> getFieldValueByAttribute(project, attribute))
				.filter(Objects::nonNull)
				.flatMap(attributeValue -> cutAttributeValue(String.valueOf(attributeValue)).stream())
				.collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

		List<Item> items = statisticsMap.entrySet().stream()
				.map(entry -> new Item(String.valueOf(entry.getKey()), entry.getValue().intValue()))
				.sorted(Comparator.comparingInt(Item::getCount).reversed())
				.toList();

		Statistics statistics = new Statistics();
		statistics.setItems(items);

		return statistics;
	}

	private Object getFieldValueByAttribute(Project project, String attribute) {
		try {
			Optional<Field> fieldByAttribute = Arrays.stream(project.getClass().getDeclaredFields())
					.filter(declaredField -> fieldExistsByAttribute(declaredField, attribute))
					.findAny();

			if (fieldByAttribute.isEmpty()) {
				log.warn("No such field exception, return null value");
				return null;
			}

			Field field = fieldByAttribute.get();
			field.setAccessible(true);
			return field.get(project);
		} catch (IllegalAccessException e) {
			throw new RuntimeException(e);
		}
	}

	private boolean fieldExistsByAttribute(Field field, String attribute) {
		return field.getName().equals(attribute)
				|| (field.isAnnotationPresent(JsonProperty.class)
				&& field.getAnnotation(JsonProperty.class).value().equals(attribute));
	}

	private List<String> cutAttributeValue(String attributeValue) {
		return Arrays.stream(attributeValue.split(","))
				.map(String::trim)
				.toList();
	}
}
