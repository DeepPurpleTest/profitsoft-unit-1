package org.example.processor.impl;

import lombok.extern.slf4j.Slf4j;
import org.example.processor.AbstractFileProcessor;
import org.example.service.ReaderService;

@Slf4j
public class FileProcessorImpl<T> extends AbstractFileProcessor<T> {

	public FileProcessorImpl(ReaderService<T> readerService) {
		super(readerService);
	}
}
