package Online.example.com.domain.api;

import lombok.Data;

@Data
public class PaymentOrder {
	String paymentId;
	Integer amount;
	String currency;
	String paymentStatus;
}
