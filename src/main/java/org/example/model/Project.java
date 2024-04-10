package org.example.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * Model of application
 * Projection of our json data
 */
@Data
@ToString
@NoArgsConstructor
public class Project {

	private String name;

	private String members;

	private String tasks;

	private String description;

	@JsonProperty("begin_date")
	private String beginDate;
}
