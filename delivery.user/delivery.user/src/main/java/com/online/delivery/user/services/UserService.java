package com.online.delivery.user.services;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.online.delivery.user.dto.AuthRequest;
import com.online.delivery.user.entities.Customer;
import com.online.delivery.user.entities.Role;
import com.online.delivery.user.entities.User;
import com.online.delivery.user.repository.UserRepository;
import com.online.delivery.user.security.JwtUtil;
import com.online.delivery.user.util.ItemBuilder;
import com.online.delivery.user.util.JsonHelper;

@Service
public class UserService {
	Logger logger = LoggerFactory.getLogger(UserService.class);

	DynamoDB dynamoDB;
	ObjectMapper objectMapper;
	Table table;
	
	@Autowired
	JsonHelper jsonHelper;
	@Autowired
	ItemBuilder itemBuilder;
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired 
	JwtUtil jwtUtil;
	
	 @Autowired
	    private PasswordEncoder passwordEncoder;
	
	

	public UserService(DynamoDB dynamoDB, ObjectMapper objectMapper) {
		this.dynamoDB = dynamoDB;
		this.objectMapper = objectMapper;
		this.table = dynamoDB.getTable("Users");
	}
	
	// User Register
	public User addUser(User user) {
        user.setRole(user.getUserType());
        
		Item item = itemBuilder.buildItem(user);
		logger.info("item: " + item);
		table.putItem(item);
		Item itemSavedInDB = table.getItem("userId", user.getUserId());
		logger.info("itemSavedInDB:\n" + itemSavedInDB);
		return jsonHelper.getObjectFromJson(itemSavedInDB.toJSON(), User.class);
	}
	
	// Customer Register
	public User addCustomer(Customer customer) {
		// Table table = dynamoDB.getTable("Users");
		customer.setUserType(Role.CUSTOMER);

		Item item = itemBuilder.buildItem(customer);

		table.putItem(item);
		Item itemSavedInDB = table.getItem("userId", customer.getUserId());

		logger.info("itemSavedInDB:\n" + itemSavedInDB);

		return jsonHelper.getObjectFromJson(itemSavedInDB.toJSON(), User.class);
	}
	
	// Vendor Register
		public User addVendor(Customer customer) {
			// Table table = dynamoDB.getTable("Users");
			customer.setUserType(Role.VENDOR);

			Item item = itemBuilder.buildItem(customer);

			table.putItem(item);
			Item itemSavedInDB = table.getItem("userId", customer.getUserId());

			logger.info("itemSavedInDB:\n" + itemSavedInDB);

			return jsonHelper.getObjectFromJson(itemSavedInDB.toJSON(), User.class);
		}
	
		// Admin Register
		public User addAdmin(Customer customer) {
			// Table table = dynamoDB.getTable("Users");
			customer.setUserType(Role.ADMIN);

			Item item = itemBuilder.buildItem(customer);

			table.putItem(item);
			Item itemSavedInDB = table.getItem("userId", customer.getUserId());

			logger.info("itemSavedInDB:\n" + itemSavedInDB);

			return jsonHelper.getObjectFromJson(itemSavedInDB.toJSON(), User.class);
		}
		// Delivery Personnel Register
				public User addDeliveryPersonnel(Customer customer) {
					// Table table = dynamoDB.getTable("Users");
					customer.setUserType(Role.DELIVERY_PERSONNEL);

					Item item = itemBuilder.buildItem(customer);

					table.putItem(item);
					Item itemSavedInDB = table.getItem("userId", customer.getUserId());

					logger.info("itemSavedInDB:\n" + itemSavedInDB);

					return jsonHelper.getObjectFromJson(itemSavedInDB.toJSON(), User.class);
				}
				
				
				// JWT
				public User registerUser(User user) {
			        // Encode password before saving
			        user.setPassword(passwordEncoder.encode(user.getPassword()));
			        userRepository.save(user);
			        return user;
			    }

			    /**
			     * Authenticates a user and generates a JWT token.
			     */
			    public String authenticateUser(AuthRequest authRequest) {
			        // Retrieve user by Email
			        Optional<User> userOptional = userRepository.findById(authRequest.getEmail());
			        if (userOptional.isEmpty()) {
			            throw new RuntimeException("User not found");
			        }

			        User user = userOptional.get();

			        // Verify password
			        if (!passwordEncoder.matches(authRequest.getPassword(), user.getPassword())) {
			            throw new RuntimeException("Invalid credentials");
			        }

			        // Generate JWT token
			        return jwtUtil.generateToken(user);
			    }

}
