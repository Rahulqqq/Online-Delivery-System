package Online.example.com.domain.api;

import lombok.Data;

@Data
public class DeliveryDetails {
	String deliveryId;
	String personnelId;
	String deliverTo;
	String pickFrom;
	String deliveryStatus;
}
