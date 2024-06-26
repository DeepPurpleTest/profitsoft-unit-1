package util;

import lombok.experimental.UtilityClass;
import org.example.model.xml.Item;
import org.example.model.xml.Statistics;

import java.io.InputStream;
import java.util.List;

@UtilityClass
public class DataUtils {
	public static Statistics getValidStatistics() {
		Statistics statistics = new Statistics();

		Item item1 = new Item("Alice", 2L);
		Item item2 = new Item("John", 2L);
		Item item3 = new Item("Emily", 1L);
		Item item4 = new Item("Bob", 1L);
		Item item5 = new Item("David", 1L);

		List<Item> items = List.of(item1, item2, item3, item4, item5);
		statistics.setItems(items);

		return statistics;
	}

	public static InputStream getResourceAsStream(String resource) {
		return DataUtils.class.getResourceAsStream(resource);
	}
}
