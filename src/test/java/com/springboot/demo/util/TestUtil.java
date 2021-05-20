package com.springboot.demo.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.springboot.demo.dto.CustomerDto;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class TestUtil {


	public static String mapToJson(final Object object) throws JsonProcessingException {
		final ObjectMapper objectMapper = new ObjectMapper();
		return objectMapper.writeValueAsString(object);
	}

	public static CustomerDto mapToObject(final String json) throws JsonProcessingException {
		final ObjectMapper objectMapper = new ObjectMapper();
		return objectMapper.readValue(json, CustomerDto.class);
	}
	
}
