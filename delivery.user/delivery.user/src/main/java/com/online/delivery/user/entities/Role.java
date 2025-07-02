package com.online.delivery.user.entities;

import org.springframework.security.core.GrantedAuthority;

public enum Role {
	CUSTOMER, VENDOR, ADMIN, DELIVERY_PERSONNEL;
}

class RoleGrantedAuthority implements GrantedAuthority {
	private static final long serialVersionUID = -3408298481881657796L;
	String role;

	public RoleGrantedAuthority(String role) {
		this.role = role;
	}

	@Override
	public String getAuthority() {
		return this.role;
	}
	
	@Override
	public String toString() {
		return this.role.toString();
	}

}
