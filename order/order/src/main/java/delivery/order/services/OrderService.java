package delivery.order.services;



import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import Online.example.com.domain.api.Order;
import Online.example.com.domain.api.OrderDetails;
import Online.example.com.domain.api.PaymentOrder;
import delivery.order.repository.OrderRepository;

@Service
public class OrderService {
	
	
	Logger logger = LoggerFactory.getLogger(OrderService.class);

    @Autowired
    private RestTemplate restTemplate;
    
    public PaymentOrder callPaymentPostEndpoint(OrderDetails orderDetails) {
        String url = "http://localhost:8087/payment/create";
        logger.info("Calling Payment API: " + url);
        ResponseEntity<PaymentOrder> response = restTemplate.postForEntity(url, orderDetails, PaymentOrder.class);
        logger.info("Response from Payment API: " + response.toString());
        return (PaymentOrder) response.getBody();
    }
	
    @Autowired
    private OrderRepository orderRepository;

    public Order createOrder(Order order) {
        // Add order ID and set default status
        order.setOrderId("ORDER-" + System.currentTimeMillis());
        order.setOrderStatus("PENDING");
        
        OrderDetails orderDetails = new OrderDetails();
        orderDetails.setAmount(order.getTotalAmount());
        orderDetails.setCurrency("INR");
        PaymentOrder paymentDeatils = callPaymentPostEndpoint(orderDetails);
        order.setPaymentId(paymentDeatils.getPaymentId());
        
        logger.info("Saving order to database: " + order.toString());
        return orderRepository.save(order);
    }
    
    
    public Order viewOrder(String id) {
//        return orderRepository.findById(id).orElseThrow(() -> new RuntimeException("Order not found"));
    	return orderRepository.getOrder(id);
    }

    public List<Order> listOrders() {
        return orderRepository.findAll();
    }

    // Update order status
    public Order updateOrder(String id, Order updatedOrder) {
		Order order = viewOrder(id);
		order.setOrderItems(updatedOrder.getOrderItems());
		order.setTotalAmount(updatedOrder.getTotalAmount());
		return orderRepository.save(order);
    }
    

    public String deleteOrder(String id) {
        Order order = viewOrder(id); 
        orderRepository.deleteById(id);
        return "Order deleted successfully";
    }


	public Order updateOrderStatus(String id, Order order) {
		Order orderToUpdate = viewOrder(id);
		logger.info("Updating order status to: " + order.getOrderStatus());
		orderToUpdate.setOrderStatus(order.getOrderStatus());
		logger.info("Order status updated successfully");
		return orderRepository.save(orderToUpdate);
	}


}

