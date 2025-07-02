
package delivery.order.repository;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.ItemCollection;
import com.amazonaws.services.dynamodbv2.document.ScanOutcome;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.document.spec.ScanSpec;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import Online.example.com.domain.api.Order;
import delivery.order.utils.JsonHelper;

@Repository
public class OrderRepository {

	private final Table orderTable;
	private final DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;

	@Autowired
	JsonHelper jsonHelper;

	@Autowired
	ObjectMapper objectMapper;
	Logger logger = LoggerFactory.getLogger(JsonHelper.class);

	@Autowired
	public OrderRepository(DynamoDB dynamoDB) {
		this.orderTable = dynamoDB.getTable("Orders");
	}

	
	// Add order
	public Order save(Order order) {
		Item item = null;
		try {
			item = new Item().withPrimaryKey("orderId", order.getOrderId())
					.withString("userId", order.getUserId())
					.withString("paymentId", order.getPaymentId())
					.withString("vendorAddress", order.getVendorAddress())
					.withString("orderDate", order.getOrderDate().format(formatter))
					.withString("orderStatus", order.getOrderStatus())
					.withString("customerAddress", order.getCustomerAddress())
					.withNumber("totalAmount", order.getTotalAmount())
					.withJSON("orderItems", objectMapper.writeValueAsString(order.getOrderItems()));
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		orderTable.putItem(item);
		return order;
	}

	// Find order by ID
//	public Optional<Order> findById(String id) {
//		Item item = orderTable.getItem("orderId", id);
//		if (item == null)
//			return Optional.empty();
//
//		Order order = new Order();
//		order.setOrderId(item.getString("orderId"));
//		order.setUserId(item.getString("userId"));
//		order.setOrderStatus(item.getString("status"));
//		order.setVendorAddress(item.getString("vendorAddress"));
//		order.setCustomerAddress(item.getString("customerAddress"));
//		order.setOrderDate(LocalDateTime.parse(item.getString("orderDate"), formatter));
//		order.setTotalAmount(item.getNumber("totalAmount").doubleValue());
//		// Parse JSON string to List<OrderItem>
//		// order.setItems(objectMapper.readValue(item.getString("items"), new
//		// TypeReference<List<OrderItem>>() {}));
//
//		return Optional.of(order);
//	}

	// Find order by ID
	public Order getOrder(String id) {
		Item item = orderTable.getItem("orderId", id);
		if (item == null) {
			throw new RuntimeException("Order not found with orderId: " + id);
		}

		return jsonHelper.getObjectFromJson(item.toJSON(), Order.class);
	}

	
	// Update order
	public List<Order> findAll() {
		// Implement scanning logic for DynamoDB

		ScanSpec scanspec = new ScanSpec()
				.withProjectionExpression("orderId, userId, paymentId, orderDate, orderStatus, totalAmount, orderItems, vendorAddress, customerAddress")

				.withFilterExpression("attribute_exists(orderId)");

		ItemCollection<ScanOutcome> items = orderTable.scan(scanspec);

		List<Order> orders = new ArrayList<>();

		for (Item item : items) {
			Order order = jsonHelper.getObjectFromJson(item.toJSON(), Order.class);
			orders.add(order);
		}
		return orders;
	}

		// Update order status
	public Order updateOrderStatus(String id, Order order) {
		Item item = orderTable.getItem("orderId", id);
		if (item == null) {
			throw new RuntimeException("Order not found with orderId: " + id);
		}
		item.withString("orderStatus", order.getOrderStatus());
		orderTable.putItem(item);
		return order;
	}
	
	
	//Delete Product
	public Item deleteById(String id) {
		orderTable.deleteItem("orderId", id);
		Item item = orderTable.getItem("orderId", id);
		
		if(item == null) {
			throw new RuntimeException("Order not found with orderId: " + id);
		}
		orderTable.deleteItem("orderId", id);
		return item;
	}
}

