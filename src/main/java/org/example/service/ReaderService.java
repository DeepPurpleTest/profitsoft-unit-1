package org.example.service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * Provides interface for work with json data
 */
public interface ReaderService<T> {
	InputStream getStreamOfData(String path) throws FileNotFoundException;

	List<T> parseJson(InputStream data) throws IOException;
}
