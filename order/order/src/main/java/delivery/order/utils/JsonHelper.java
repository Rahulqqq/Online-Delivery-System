package delivery.order.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import Online.example.com.domain.api.Order;

@Component
public class JsonHelper {
	@Autowired
	ObjectMapper objectMapper;
	Logger logger = LoggerFactory.getLogger(JsonHelper.class);
	
	public Order getObjectFromJson(String json, Class<Order> clazz) {

		try {
			return objectMapper.readValue(json, clazz);
		} catch (Exception ex) {
			logger.error("Error reading json", ex);
		}

		return null;
	}
}
