package com.online.delivery.user.repository;

import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.online.delivery.user.entities.User;
import com.online.delivery.user.entities.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public class UserRepository {

    private final Table userTable;

    
    public UserRepository(DynamoDB dynamoDB) {
        this.userTable = dynamoDB.getTable("Users");
    }

    
    // Save a user to the Users table.
     
    public void save(User user) {
        Item item = new Item()
                .withPrimaryKey("userId", user.getUserId())
                .withString("name", user.getUsername())
                .withString("email", user.getEmail())
                .withString("password", user.getPassword())
                .withString("phoneNumber", user.getPhoneNumber());
        
        // Optional: Add roles as a JSON string
//        if (user.getRoles() != null) {
//            item.withJSON("roles", user.getRoles().toString());
//        }

        userTable.putItem(item);
    }

    // Find a user by username.
     
    
    public Optional<User> findById(String userId) {
        Item item = userTable.getItem("userId", userId);

        if (item == null) {
            return Optional.empty();
        }

        User user = new User();
        user.setUserId(item.getString("userId"));
        user.setUsername(item.getString("name"));
        user.setEmail(item.getString("email"));
        user.setPassword(item.getString("password"));
        user.setPhoneNumber(item.getString("phoneNumber"));
//        user.setRole(item.getString("role"));
        // Optional: Parse roles from JSON string
//        if (item.isPresent("roles")) {
//            user.setRoles(parseRoles(item.getJSON("roles")));
//        }

        return Optional.of(user);
    }

    /**
     * Parse roles from JSON string (helper method).
     */
    private Set<Role> parseRoles(String rolesJson) {
        // Example of parsing roles from JSON string
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.readValue(rolesJson, new TypeReference<Set<Role>>() {});
        } catch (Exception e) {
            throw new RuntimeException("Failed to parse roles", e);
        }
    }

	public Optional<User> findById(Object username) {
		// TODO Auto-generated method stub
		return null;
	}
}
