package delivery.order.utils;

import org.springframework.stereotype.Component;

import com.amazonaws.services.dynamodbv2.document.Item;

import Online.example.com.domain.api.Order;

@Component
public class ItemBuilder {

		public Item buildItem(Order order) {
			Item item = new Item();
			item.with("orderId", order.getOrderId());
			item.with("userId", order.getUserId());
			item.with("orderDate", order.getOrderDate().toString());
			item.with("status", order.getOrderStatus());
			item.with("totalAmount", order.getTotalAmount());
			item.with("items", order.getOrderItems().toString());
			return item;
			
		}
	
}
