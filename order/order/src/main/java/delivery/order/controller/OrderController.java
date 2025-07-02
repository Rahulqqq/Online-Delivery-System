package delivery.order.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import Online.example.com.domain.api.Order;
import delivery.order.services.OrderService;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/create")
    public ResponseEntity<Object> createOrder(@RequestBody Order order) {
		try {
			return ResponseEntity.ok(orderService.createOrder(order));
		} catch (Exception e) {
			return ResponseEntity.status(500).body(e.getMessage());
		}
    }

    @GetMapping("/view/{id}")
    public ResponseEntity<Object> viewOrder(@PathVariable String id) {
		try {
			return ResponseEntity.ok(orderService.viewOrder(id));
		} catch (Exception e) {
			return ResponseEntity.status(400).body(e.getMessage());
		}
    }

    @GetMapping("/list")
    public ResponseEntity<Object> listOrders() {
		try {
			return ResponseEntity.ok(orderService.listOrders());
		} catch (Exception e) {
			return ResponseEntity.status(400).body(e.getMessage());
		}
    }

//    @PutMapping("/update/{id}")
//    public ResponseEntity<Object> updateOrder(@PathVariable String id, @RequestBody Order order) {
//		try {
//			return ResponseEntity.ok(orderService.updateOrder(id, order));
//		} catch (Exception e) {
//			return ResponseEntity.status(400).body(e.getMessage());
//		}
//    }
    
    @PatchMapping("/update/{id}")
	public ResponseEntity<Object> updateOrderStatus(@PathVariable String id, @RequestBody Order order) {
		try {
			return ResponseEntity.ok(orderService.updateOrderStatus(id, order));
		} catch (Exception e) {
			return ResponseEntity.status(400).body(e.getMessage());
		}
	}

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Object> deleteById(@PathVariable String id) {
		try {
			return ResponseEntity.ok(orderService.deleteOrder(id));
		} catch (Exception e) {
			return ResponseEntity.status(400).body(e.getMessage());
		}
    }
}
