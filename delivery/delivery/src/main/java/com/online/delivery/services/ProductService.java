package com.online.delivery.services;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.ItemCollection;
import com.amazonaws.services.dynamodbv2.document.ScanOutcome;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.document.spec.ScanSpec;
import com.amazonaws.services.dynamodbv2.document.utils.ValueMap;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.online.delivery.entities.Product;
import com.online.delivery.util.ItemBuilder;

@Service
public class ProductService {
	Logger logger = LoggerFactory.getLogger(ProductService.class);

	DynamoDB dynamoDB;
	ObjectMapper objectMapper;
	Table table;
	
	@Autowired
	ItemBuilder itemBuilder;

	public ProductService(DynamoDB dynamoDB, ObjectMapper objectMapper) {
		this.dynamoDB = dynamoDB;
		this.objectMapper = objectMapper;
		this.table = dynamoDB.getTable("Products");
	}

	// Add product
	public Product addProduct(Product product) {
		// Table table = dynamoDB.getTable("Products");

		Item item = itemBuilder.buildItem(product);

		table.putItem(item);
		Item itemSavedInDB = table.getItem("productId", product.getProductId());

		logger.info("itemSavedInDB:\n" + itemSavedInDB);

		return getObjectFromJson(itemSavedInDB.toJSON(), Product.class);

	}

	
	
	// Add product By Vendor
		public Product addProductByVendor(Product product) {
			// Table table = dynamoDB.getTable("Products");
			logger.info("Product object before building item: " + product);
			Item item = itemBuilder.buildItem(product);
			
			 logger.info("Item being saved to DynamoDB: " + item.toJSONPretty());

			table.putItem(item);
			Item itemSavedInDB = table.getItem("productId", product.getProductId());

			logger.info("itemSavedInDB:\n" + itemSavedInDB);

			return getObjectFromJson(itemSavedInDB.toJSON(), Product.class);

		}
		

// Get a product by Id
public Product getProduct(String productId) {
   
        // Fetch item from DynamoDB
        Item item = table.getItem("productId", productId);
        return getObjectFromJson(item.toJSON(), Product.class);
	
	}


//Get All Products
public List<Product> getAllProducts(){
	ScanSpec scanspec = new ScanSpec()
			.withProjectionExpression("productId, productName, description, price, quantity, productSubType, productType, vendorId")
			.withFilterExpression("attribute_exists(productId)");
	
	
	ItemCollection<ScanOutcome> items = table.scan(scanspec);
	
	List<Product> products = new ArrayList<>();
	
	for(Item item : items) {
		Product product = getObjectFromJson(item.toJSON(), Product.class);
		products.add(product);
	}
	return products;
}


//Get All Products By vendor Id
public List<Product> getProductsByVendorId(String vendorId) {
	ValueMap map = new ValueMap().with(":vendorId", vendorId);
	ScanSpec scanspec = new ScanSpec()
			.withProjectionExpression("productId, productName, description, price, quantity, productSubType, productType, vendorId")
			.withFilterExpression("attribute_exists(vendorId) and vendorId = :vendorId")
			.withValueMap(map);
	logger.info(scanspec.toString());
	ItemCollection<ScanOutcome> items = table.scan(scanspec);
	List<Product> products = new ArrayList<>();
	for(Item item : items) {
		Product product = getObjectFromJson(item.toJSON(), Product.class);
		products.add(product);
	}
	logger.info("found" + products);
	
	return products;
}


//Update Product
public Product updateProduct(Product product) {
	String s = product.getProductId();
	 Item itemFromDB = table.getItem("productId", s);
	
	 
	 if(itemFromDB == null) {
		 return null;
	 }
	 Item itemToBeUpdatedItem = itemBuilder.buildItem(product);
		logger.info("itemSavedInDB:\n" + itemToBeUpdatedItem);

		table.putItem(itemToBeUpdatedItem);
		return getObjectFromJson(itemToBeUpdatedItem.toJSON(), Product.class);
	
}

//Delete Product
public Item deleteProduct(String productId) {
	Item item = table.getItem("productId", productId);
	
	if(item == null) {
		return null;
	}
	table.deleteItem("productId", productId);
	return item;
}

private Product getObjectFromJson(String json, Class<Product> clazz) {

	try {
		return objectMapper.readValue(json, clazz);
	} catch (Exception ex) {
		logger.error("Error reading json", ex);
	}

	return null;
}

}