package Online.example.com.domain.api;

import java.time.LocalDateTime;
import java.util.List;

import lombok.Data;

@Data
public class Order {
	private String orderId;
	private String paymentId;
	private String userId; // Reference to the user placing the order
	private LocalDateTime orderDate;
	private List<String> orderItems; // List of items in the order
	private double totalAmount;
	private String orderStatus; // e.g., PENDING, COMPLETED, CANCELLED
	private String vendorAddress;
	private String customerAddress;
}
