package com.online.delivery.user.entities;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;

@Data
@NoArgsConstructor
@AllArgsConstructor
@DynamoDbBean
public  class User implements UserDetails {
	
	private static final long serialVersionUID = 1L;

	private String userId;
	private String username;
	private String email;
	private String password;
	private String phoneNumber;
	private Role userType;// Enum for roles like ADMIN, USER, etc.
	
	@Enumerated(EnumType.STRING)
	private Role role; // A user can have only one role

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
	    return List.of(new RoleGrantedAuthority(role.name()));
	}
	
	
//	private Set<Role> roles;// A user can have multiple roles
//
//	@Override
//	public Collection<? extends GrantedAuthority> getAuthorities() {
//		
//        return this.roles.stream()
//                .map(r -> new RoleGrantedAuthority(r.name()))
//                .collect(Collectors.toList());
//	}

	@Override
	public String getUsername() {
		return this.username;
	}

	public User(String username, String password, Role role) {
        this.username = username;
        this.password = password;
        this.role = role;
	}
	
//	public String getRole() {
//        return this.role.toString();
//    }
//	
//	@JsonCreator
//	public static Role fromString(@JsonProperty("role") String role) {
//	    return Role.valueOf(role.toUpperCase());
//	}
}
