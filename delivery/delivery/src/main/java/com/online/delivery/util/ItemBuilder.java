package com.online.delivery.util;

import org.springframework.stereotype.Service;

import com.amazonaws.services.dynamodbv2.document.Item;
import com.online.delivery.entities.Product;

@Service
public class ItemBuilder {

		public Item buildItem(Product product) {
			Item item = new Item();
			item.with("productId", product.getProductId());
			item.with("vendorId", product.getVendorId());
			item.with("productName", product.getProductName());
			item.with("description", product.getDescription());
			item.with("price", product.getPrice());
			item.with("quantity", product.getQuantity());
			item.with("productSubType", product.getProductSubType());
			item.with("productType", product.getProductType());
			
			return item;
		}
	
}
