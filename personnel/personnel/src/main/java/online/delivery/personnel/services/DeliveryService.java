package online.delivery.personnel.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.amazonaws.services.dynamodbv2.document.Table;
import com.fasterxml.jackson.databind.ObjectMapper;

import online.delivery.personnel.utils.JsonHelper;

@Service
public class DeliveryService {
	
	@Autowired
	ObjectMapper objectMapper;
	
	@Autowired
	JsonHelper jsonHelper;
	
	@Autowired
	Table table;
	
	/*
	 * methods to save delivery details in table
	 * 
	 * */
	

}
