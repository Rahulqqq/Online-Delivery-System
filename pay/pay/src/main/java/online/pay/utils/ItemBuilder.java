package online.pay.utils;

import org.springframework.stereotype.Component;

import com.amazonaws.services.dynamodbv2.document.Item;

import online.pay.model.PaymentOrder;

@Component
public class ItemBuilder {

		public Item buildItem(PaymentOrder order) {
			Item item = new Item();
			item.with("paymentId", order.getPaymentId());
			item.with("amount", order.getAmount());
			item.with("currency", order.getCurrency());
			item.with("paymentStatus", order.getPaymentStatus());
			return item;
		}
}
