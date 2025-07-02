package com.online.delivery.user.services;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.ItemCollection;
import com.amazonaws.services.dynamodbv2.document.ScanOutcome;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.document.spec.ScanSpec;
import com.amazonaws.services.dynamodbv2.document.utils.ValueMap;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.online.delivery.user.entities.User;
import com.online.delivery.user.util.ItemBuilder;
import com.online.delivery.user.util.JsonHelper;

@Service
public class CustomeUserDetailsService implements UserDetailsService {

	Logger logger = LoggerFactory.getLogger(Service.class);

	DynamoDB dynamoDB;
	Table table;

	ObjectMapper objectMapper;

	@Autowired
	ItemBuilder itemBuilder;

	@Autowired
	JsonHelper jsonHelper;

	@Autowired
	public CustomeUserDetailsService(DynamoDB dynamoDB, ObjectMapper objectMapper) {
		this.dynamoDB = dynamoDB;
		this.objectMapper = objectMapper;
		this.table = dynamoDB.getTable("Users");
	}

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		// Fetch item from DynamoDB
		ValueMap map = new ValueMap().with(":email", email);
		ScanSpec scanspec = new ScanSpec()
				.withProjectionExpression("userId, username, email, password, phoneNumber, userType")
				.withFilterExpression("attribute_exists(email) AND email = :email").withValueMap(map);
		ItemCollection<ScanOutcome> items = table.scan(scanspec);

		List<User> products = new ArrayList<>();
		for (Item item : items) {
			User user = jsonHelper.getObjectFromJson(item.toJSON(), User.class);
			products.add(user);
		}
		User user = products.get(0);
		user.setRole(user.getUserType());
		logger.info("Found user" + user);
		return user;

	}

}
