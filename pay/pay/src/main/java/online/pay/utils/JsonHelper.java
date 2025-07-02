package online.pay.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.amazonaws.services.dynamodbv2.document.Item;
import com.fasterxml.jackson.databind.ObjectMapper;

import online.pay.model.PaymentOrder;

@Component
public class JsonHelper {
	@Autowired
	ObjectMapper objectMapper;
	Logger logger = LoggerFactory.getLogger(JsonHelper.class);
	
	public PaymentOrder getObjectFromJson(String json, Class<PaymentOrder> clazz) {

		try {
			return objectMapper.readValue(json, clazz);
		} catch (Exception ex) {
			logger.error("Error reading json", ex);
		}

		return null;
	}
}
