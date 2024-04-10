package util;

import lombok.experimental.UtilityClass;
import org.example.model.xml.Item;
import org.example.model.xml.Statistics;

import java.util.List;

@UtilityClass
public class Data {
	public static Statistics getValidStatistics() {
		Statistics statistics = new Statistics();

		Item item1 = new Item("Alice", 2);
		Item item2 = new Item("John", 2);
		Item item3 = new Item("Emily", 1);
		Item item4 = new Item("Bob", 1);
		Item item5 = new Item("David", 1);

		List<Item> items = List.of(item1, item2, item3, item4, item5);
		statistics.setItems(items);

		return statistics;
	}

	public static String  getValidJsonData() {
		return """
				[
				  {
				    "name": "Project A",
				    "members": "John, Alice, Bob",
				    "tasks": "Task 1, Task 2, Task 3",
				    "description": "Description of Project A",
				    "begin_date": "2024-04-09"
				  },
				  {
				    "name": "Project B",
				    "members": "John, Emily, David, Alice",
				    "tasks": "Task 1, Task 4, Task 5",
				    "description": "Description of Project B",
				    "begin_date": "2024-04-10"
				  }
				]
				""";
	}
	public static String  getInvalidJsonData() {
		return """
				  {
				    "name": "Project A",
				    "members": "John, Alice, Bob",
				    "tasks": "Task 1, Task 2, Task 3",
				    "description": "Description of Project A",
				    "begin_date": "2024-04-09"
				  }
				""";
	}
}
