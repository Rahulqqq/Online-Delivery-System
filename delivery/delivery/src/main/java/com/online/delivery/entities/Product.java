package com.online.delivery.entities;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.Data;


@Data
public class Product {
	
		
		 	private  String productId;
		 	
		 	private String vendorId;
		
		    private  String productName;
		    private  String description;
		    private  Integer price;
		    private  Integer quantity;
		    private  String productSubType;
		    private  String productType;
		    
		    public Product(String productId, String vendorId, String productName, String description, Integer price, Integer quantity, String productSubType, String productType) {
		    	this.productId = productId;
		    	this.productName = productName;
		    	this.description  = description;
		    	this.price = price;
		    	this.quantity = quantity;
		    	this.productSubType = productSubType;
		    	this.productType = productType;
		    	this.vendorId = vendorId;
		    }
		    
		    @Override
		    public String toString() {
		    	try {
		    		return new ObjectMapper().writeValueAsString(this);
		    	}catch (Exception ex){
		    		return "";
		    		
		    	} 
		    	
		   	}
}


