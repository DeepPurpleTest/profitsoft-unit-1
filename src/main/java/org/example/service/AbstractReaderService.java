package org.example.service;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Abstract class providing implementation of ReaderService methods for inheritance.
 * This class serves as a foundation for creating specific reader services.
 *
 * @param <T> the type of objects to be read by implementations
 */
@Slf4j
@AllArgsConstructor
public abstract class AbstractReaderService<T> implements ReaderService<T> {

	protected final ObjectMapper objectMapper;

	/**
	 * Returns the class type of objects processed by this reader service.
	 *
	 * @return the class type of objects
	 */
	public abstract Class<T> getType();

	/**
	 * Method for read data from file
	 */
	@Override
	public InputStream getStreamOfData(String path) throws FileNotFoundException {
		try {
			return new FileInputStream(path);
		} catch (FileNotFoundException e) {
			throw new FileNotFoundException("Resource not found: " + path);
		}
	}

	/**
	 * Reads JSON data from the provided input stream and converts it into a list of objects of type T.
	 *
	 * @param data the input stream containing JSON data
	 * @return a list of objects parsed from the JSON data
	 * @throws IOException if an I/O error occurs during JSON parsing
	 */
	@Override
	public List<T> parseJson(InputStream data) throws IOException {
		List<T> objects = new ArrayList<>();

		try (JsonParser jsonParser = objectMapper.getFactory().createParser(data)) {

			if (jsonParser.nextToken() != JsonToken.START_ARRAY) {
				throw new IllegalStateException("Expected content to be an array");
			}

			while (jsonParser.nextToken() != JsonToken.END_ARRAY) {
				T object = objectMapper.readValue(jsonParser, getType());
				objects.add(object);
			}
		}

		return objects;
	}
}
