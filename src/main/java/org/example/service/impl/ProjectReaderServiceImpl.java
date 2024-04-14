package org.example.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.example.model.Project;
import org.example.service.AbstractReaderService;

/**
 * Realization of ReaderService for Project type
 */
@Slf4j
public class ProjectReaderServiceImpl extends AbstractReaderService<Project> {

	public ProjectReaderServiceImpl(ObjectMapper objectMapper) {
		super(objectMapper);
	}

	@Override
	public Class<Project> getType() {
		return Project.class;
	}
}
