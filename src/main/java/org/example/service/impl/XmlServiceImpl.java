package org.example.service.impl;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.model.xml.Statistics;
import org.example.service.XmlService;

import java.io.File;
import java.io.IOException;

@Slf4j
@AllArgsConstructor
public class XmlServiceImpl implements XmlService {

	private final XmlMapper xmlMapper;

	@Override
	public void writeInFile(Statistics statistics, String attribute) {
		String fileName = String.format("statistics_by_%s.xml", attribute);

		try {
			xmlMapper.writeValue(new File(fileName), statistics);
			log.info("Statistics is created!");
		} catch (IOException e) {
			log.error("Exception while writing in xml file");
			throw new RuntimeException(e);
		}
	}
}
