package online.pay.model;

import lombok.Data;

@Data
public class OrderDetails {
	Double amount;
	String currency;
}
