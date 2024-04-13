package org.example.model.xml;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Projection of output data
 */
@Data
@AllArgsConstructor
public class Item {

	private String value;
	private Long count;
}
