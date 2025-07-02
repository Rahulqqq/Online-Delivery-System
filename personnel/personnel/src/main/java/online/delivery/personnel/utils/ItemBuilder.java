package online.delivery.personnel.utils;

import org.springframework.stereotype.Component;

import com.amazonaws.services.dynamodbv2.document.Item;

import Online.example.com.domain.api.DeliveryDetails;

@Component
public class ItemBuilder {

		public Item buildItem(DeliveryDetails delivery) {
			Item item = new Item();
			item.withString("deliveryId", delivery.getDeliveryId());
			item.withString("personnelId", delivery.getPersonnelId());
			item.withString("deliverTo", delivery.getDeliverTo());
			item.withString("pickFrom", delivery.getPickFrom());
			item.withString("deliveryStatus", delivery.getDeliveryStatus());
			return item;
		}
}
