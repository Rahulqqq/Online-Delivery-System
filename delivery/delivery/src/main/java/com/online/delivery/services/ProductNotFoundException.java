package com.online.delivery.services;

@SuppressWarnings("serial")
public class ProductNotFoundException extends Exception {
	public ProductNotFoundException(String message) {
        super(message);
	}
}
