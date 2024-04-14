package org.example.util;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Optional;

/**
 * Utility class for work with Class types
 */
@Slf4j
@UtilityClass
public class ReflectionUtils {

	/**
	 * Method checks if attribute exists in given type
	 * @param attribute key of json
	 * @param type in which class find key
	 */
	public static boolean isAttributeExists(String attribute, Class<?> type) {
		Optional<Field> field = Arrays.stream(type.getDeclaredFields())
				.filter(declaredField -> isFieldExistsByAttribute(declaredField, attribute))
				.findAny();

		return field.isPresent();
	}

	/**
	 * Method return actual field name
	 */
	public static String getFieldNameByAttribute(String attribute, Class<?> type) throws NoSuchFieldException {
		Optional<Field> field = Arrays.stream(type.getDeclaredFields())
				.filter(declaredField -> isFieldExistsByAttribute(declaredField, attribute))
				.findAny();

		if (field.isEmpty()) {
			throw new NoSuchFieldException();
		}

		return field.get().getName();
	}


	public static Object getFieldValue(Object object, String fieldName) {
		try {
			Field field = object.getClass().getDeclaredField(fieldName);
			field.setAccessible(true);
			return field.get(object);
		} catch (IllegalAccessException e) {
			log.error("Error during getting access to field: ", e);
			throw new RuntimeException(e);
		} catch (NoSuchFieldException e) {
			log.error("Field not found: ", e);
			throw new RuntimeException(e);
		}
	}

	/**
	 * Method tries to find field in object with same name as attribute
	 * or by @JsonProperty value
	 * @param field to check name
	 */
	private static boolean isFieldExistsByAttribute(Field field, String attribute) {
		return field.getName().equals(attribute)
				|| (field.isAnnotationPresent(JsonProperty.class)
				&& field.getAnnotation(JsonProperty.class).value().equals(attribute));
	}
}
