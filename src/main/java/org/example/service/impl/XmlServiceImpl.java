package org.example.service.impl;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.model.xml.Statistics;
import org.example.service.XmlWriterService;

import java.io.File;
import java.io.IOException;

@Slf4j
@AllArgsConstructor
public class XmlWriterServiceImpl implements XmlWriterService {

	private final XmlMapper xmlMapper;

	public void writeInFile(Statistics statistics, String attribute) throws IOException {
		xmlMapper.writeValue(new File("statistics_by_" + attribute + ".xml"), statistics);
	}
}
