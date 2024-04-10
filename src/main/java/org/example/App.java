package org.example;

import lombok.extern.slf4j.Slf4j;
import org.example.context.AppContext;
import org.example.controller.AppController;

import java.nio.file.NoSuchFileException;

@Slf4j
public class App {

	private static final AppController APP_CONTROLLER = AppContext.getInstance().getAppController();

	public static void main(String[] args) {
		log.info("Application -> start");

		try {
			APP_CONTROLLER.processData(args[0], args[1]);
		} catch (NoSuchFileException e) {
			log.error(String.format("Directory not found with path: %s", e.getMessage()));
		} catch (NoSuchFieldException e) {
			log.error(String.format("Attribute not found: %s", e.getMessage()));
		}
	}
}
