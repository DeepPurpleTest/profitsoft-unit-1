package org.example.service;

import org.example.model.Project;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * Provides interface for work with json data
 */
public interface ReaderService {
	InputStream getStreamOfData(String path) throws FileNotFoundException;

	List<Project> parseJson(InputStream data) throws IOException;
}
