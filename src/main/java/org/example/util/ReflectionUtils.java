package org.example.service.impl;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.example.model.Project;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Optional;

@Slf4j
@UtilityClass
public class ReflectionUtils {

	public static boolean isAttributeExists(String attribute, Class<?> type) {
		Optional<Field> field = Arrays.stream(type.getDeclaredFields())
				.filter(declaredField -> isFieldExistsByAttribute(declaredField, attribute))
				.findAny();

		return field.isPresent();
	}

	public static String getFieldNameByAttribute(String attribute, Class<?> type) throws NoSuchFieldException {
		Optional<Field> field = Arrays.stream(type.getDeclaredFields())
				.filter(declaredField -> isFieldExistsByAttribute(declaredField, attribute))
				.findAny();

		if (field.isEmpty()) {
			throw new NoSuchFieldException();
		}

		return field.get().getName();
	}

	public static Object getFieldValue(Project project, String fieldName) {
		try {
			Field field = project.getClass().getDeclaredField(fieldName);
			field.setAccessible(true);
			return field.get(project);
		} catch (IllegalAccessException e) {
			log.error("Error during getting access to field: ", e);
			throw new RuntimeException(e);
		} catch (NoSuchFieldException e) {
			log.error("Field not found: ", e);
			throw new RuntimeException(e);
		}
	}

	private static boolean isFieldExistsByAttribute(Field field, String attribute) {
		return field.getName().equals(attribute)
				|| (field.isAnnotationPresent(JsonProperty.class)
				&& field.getAnnotation(JsonProperty.class).value().equals(attribute));
	}
}
