package org.example.service;

import org.example.model.xml.Statistics;

import java.io.IOException;

public interface XmlWriterService {
	void writeInFile(Statistics statistics, String attribute) throws IOException;
}
