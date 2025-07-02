package online.delivery.personnel.entities;

import Online.example.com.domain.api.DeliveryDetails;
import lombok.Data;

@Data
public class Personnel {
	String personnelId;
	String personnelName;
	DeliveryDetails deliveryDetails;
    
}
