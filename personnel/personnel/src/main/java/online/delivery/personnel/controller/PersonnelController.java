package online.delivery.personnel.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import Online.example.com.domain.api.DeliveryDetails;
import online.delivery.personnel.services.DeliveryService;

@RestController
@RequestMapping("/delivery")
public class PersonnelController {
	
	@Autowired
	DeliveryService deliveryService;
	
	@PostMapping("/create")
	public ResponseEntity<Object> crateDelivery(@RequestBody DeliveryDetails delivery) {
		try {
			// Create delivery using deliveryService
			return ResponseEntity.ok("");
		} catch (Exception e) {
			return ResponseEntity.status(500).body(e.getMessage());
		}
	}
	
	@PostMapping("/accept")
	public ResponseEntity<Object> acceptDelivery(@RequestBody DeliveryDetails delivery, String userId) {
		try {
			// Accept delivery for a user using deliveryService
			return ResponseEntity.ok("");
		} catch (Exception e) {
			return ResponseEntity.status(500).body(e.getMessage());
		}
	}

}
