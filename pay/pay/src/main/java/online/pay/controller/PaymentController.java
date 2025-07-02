package online.pay.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import online.pay.model.OrderDetails;
import online.pay.model.PaymentOrder;
import online.pay.services.PaymentService;



@RestController
@RequestMapping("/payment")
public class PaymentController {
	@Autowired
	private PaymentService paymentService;

	@GetMapping("/healthcheck")
	public ResponseEntity<Object> healthcheck() {
		return ResponseEntity.ok("OK");
	}

	@PostMapping("/create")
	public ResponseEntity<Object> createPayment(@RequestBody OrderDetails orderDetails) {
		try {
			PaymentOrder order = paymentService.createOrder(orderDetails.getAmount(), orderDetails.getCurrency());
			return ResponseEntity.ok(order);
		} catch (Exception e) {
			return ResponseEntity.status(500).body(e.getMessage());
		}
	}
	
	@PutMapping("/update")
	public ResponseEntity<Object> updatePaymentStatus(@RequestBody PaymentOrder payment) {
		try {
			return ResponseEntity.ok(paymentService.updateOrder(payment));
		} catch (Exception e) {
			return ResponseEntity.status(400).body(e.getMessage());
		}
	}

	@GetMapping("/status/{orderId}")
	public ResponseEntity<Object> getPaymentStatus(@PathVariable String orderId) {
		try {
			String status = paymentService.checkPaymentStatus(orderId);
			return ResponseEntity.ok(status);
		} catch (Exception e) {
			return ResponseEntity.status(400).body(e.getMessage());
		}
	}

	@GetMapping("/{orderId}")
	public ResponseEntity<Object> getPaymentDetails(@PathVariable String orderId) {
		try {
			;
			return ResponseEntity.ok(paymentService.retrivePaymentDetails(orderId));
		} catch (Exception e) {
			return ResponseEntity.status(400).body(e.getMessage());
		}
	}
}

