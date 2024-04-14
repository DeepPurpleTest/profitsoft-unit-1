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

		if (args.length < 2) {
			log.error("Not valid args length, please check readme");
		} else {
			try {
				APP_CONTROLLER.processData(args[0], args[1]);
			} catch (NoSuchFileException e) {
				log.error("Directory not found with path: {}", e.getMessage());
			} catch (NoSuchFieldException e) {
				log.error("Attribute not found: {}", e.getMessage());
			}
		}
	}
}
