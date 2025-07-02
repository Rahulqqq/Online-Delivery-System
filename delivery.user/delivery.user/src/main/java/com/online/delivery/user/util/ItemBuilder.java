package com.online.delivery.user.util;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.amazonaws.services.dynamodbv2.document.Item;
import com.online.delivery.user.entities.User;

@Component
public class ItemBuilder {

		public Item buildItem(User user) {
			Item item = new Item();
			item.with("name", user.getUsername());
			item.with("email", user.getEmail());
			item.with("password", user.getPassword());
			item.with("phoneNumber", user.getPhoneNumber());
			item.with("userId", user.getUserId());
			item.with("userType", user.getUserType().toString());
			item.with("role", user.getRole().name());
			return item;
		}
	
}
