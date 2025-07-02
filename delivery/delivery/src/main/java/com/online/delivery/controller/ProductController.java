package com.online.delivery.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.amazonaws.services.dynamodbv2.document.Item;
import com.online.delivery.entities.Product;
import com.online.delivery.services.ProductService;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
public class ProductController{
	
	Logger logger = LoggerFactory.getLogger(ProductController.class);
	
	@Autowired
	ProductService productService;
	
	@PostMapping("/product/add")
	public Product addProduct(@RequestBody Product product) {
		return productService.addProduct(product);
	}
	
	@GetMapping("/product/{productId}")
	public ResponseEntity<Product> getProduct(@PathVariable String productId) {
	   return ResponseEntity.ok(productService.getProduct(productId));
	}
	
	@GetMapping("/products")
	public List<Product> getProducts() {
		return (productService.getAllProducts());
	}
	
	@PutMapping("/product/{productId}")
	public ResponseEntity<Object> updateProduct(@PathVariable String productId, @RequestBody Product product) {
		Product productNew  = productService.updateProduct(product);
		if(!productId.equals(product.getProductId())) {
			return ResponseEntity.badRequest().body("Product Id is Different"); 
		}
		if(productNew == null) {
			return ResponseEntity.badRequest().body("Product Not Found");
		}
		
		return ResponseEntity.ok(productService.updateProduct(productNew));
	}
	
	@DeleteMapping("/product/{productId}")
		public ResponseEntity<Object> deleteProduct(@PathVariable String productId){
		Item productNew  = productService.deleteProduct(productId);
		
		if(productNew == null) {
			return ResponseEntity.badRequest().body("Product Not Found");
		}
		return ResponseEntity.ok("Successfully Deleted");
	}
	
	
	@GetMapping("/products/{vendorId}")
		public List<Product> getProductsByVendorId(@PathVariable String vendorId){
		return productService.getProductsByVendorId(vendorId);
	}
	
	@PostMapping("addProduct/{vendorId}")
	public ResponseEntity<Object> addProductByVendor(@PathVariable String  vendorId, @RequestBody Product product) {
		Product productNew1  = productService.addProductByVendor(product);
		if(!vendorId.equals(product.getVendorId())) {
			return ResponseEntity.badRequest().body("Vendor Id is different"); 
		}
		return ResponseEntity.ok(productService.addProductByVendor(productNew1));
	}
	
	
	
	
}
	



