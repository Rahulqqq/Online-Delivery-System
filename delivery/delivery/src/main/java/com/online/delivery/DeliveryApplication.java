package com.online.delivery;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.online.delivery.entities.Product;

@SpringBootApplication
public class DeliveryApplication {

	public static void main(String[] args) {
		Logger logger = LoggerFactory.getLogger(DeliveryApplication.class);
		ConfigurableApplicationContext app = SpringApplication.run(DeliveryApplication.class, args);
		
		DynamoDB dynamoDB = app.getBean(DynamoDB.class);
		Table table = dynamoDB.getTable("Products");
		for(int i =0; i<10; i++) {
			Product product = new Product(String.valueOf(i+10), String.valueOf(i+1), "Laptop", "this is laptop", 10000, 5, "Electronic", "Device");
			Item item = new Item();
			item.with("productId", product.getProductId());
			item.with("vendorId", product.getVendorId());
			item.with("productName", product.getProductName());
			item.with("description", product.getDescription());
			item.with("price", product.getPrice());
			item.with("quantity", product.getQuantity());
			item.with("productSubType", product.getProductSubType());
			item.with("productType", product.getProductType());
			logger.info("itemSavedInDB:\n" + item);

			table.putItem(item);
			Item itemSavedInDB = table.getItem("productId", product.getProductId());

			logger.info("itemSavedInDB:\n" + itemSavedInDB);
			
			
			
			
		}
	}

}
