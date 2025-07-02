package com.online.delivery.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.online.delivery.entities.Product;

@Component
public class JsonHelper {
	@Autowired
	ObjectMapper objectMapper;
	Logger logger = LoggerFactory.getLogger(JsonHelper.class);

	public Product getObjectFromJson(String json, Class<Product> clazz) {

		try {
			return objectMapper.readValue(json, clazz);
		} catch (Exception ex) {
			logger.error("Error reading json", ex);
		}

		return null;
	}
}