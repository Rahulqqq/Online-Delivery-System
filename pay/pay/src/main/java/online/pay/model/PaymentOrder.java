package online.pay.model;

import lombok.Data;

@Data
public class PaymentOrder {
	String paymentId;
	Integer amount;
	String currency;
	String paymentStatus;
}
