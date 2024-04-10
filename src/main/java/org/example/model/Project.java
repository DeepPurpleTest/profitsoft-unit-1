package org.example.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@NoArgsConstructor
public class Test {

//	@JsonProperty("test-key")
	private String test;

	@JsonProperty("test-annotation")
	private String testAnnotation;
}
