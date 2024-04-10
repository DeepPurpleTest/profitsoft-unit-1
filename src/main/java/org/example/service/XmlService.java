package org.example.service;

import org.example.model.xml.Statistics;

import java.io.IOException;

/**
 * Provides interface for work with xml files
 */
public interface XmlService {
	void writeInFile(Statistics statistics, String attribute) throws IOException;
}
