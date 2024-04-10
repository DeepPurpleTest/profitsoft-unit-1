package org.example.service;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.model.Project;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@AllArgsConstructor
public class ReaderService {

	private final ObjectMapper objectMapper;

	public InputStream getStreamOfData(String path) throws FileNotFoundException {
		try {
			return new FileInputStream(path);
		} catch (FileNotFoundException e) {
			throw new FileNotFoundException("Resource not found: " + path);
		}
	}

	public List<Project> parseJson(InputStream data) throws IOException {
		List<Project> projectList = new ArrayList<>();

		try (JsonParser jsonParser = objectMapper.getFactory().createParser(data)) {

			if (jsonParser.nextToken() != JsonToken.START_ARRAY) {
				throw new IllegalStateException("Expected content to be an array");
			}

			while (jsonParser.nextToken() != JsonToken.END_ARRAY) {
				Project project = objectMapper.readValue(jsonParser, Project.class);
				projectList.add(project);
			}
		}

		return projectList;
	}
}
