package online.pay.services;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.razorpay.Order;
import com.razorpay.RazorpayClient;

import online.pay.configuration.RazorpayConfig;
import online.pay.model.PaymentOrder;
import online.pay.utils.ItemBuilder;
import online.pay.utils.JsonHelper;

@Service
public class PaymentService {

	private final Table table;

	@Autowired
	JsonHelper jsonHelper;
	
	@Autowired
	ItemBuilder itemBuilder;

	@Autowired
	ObjectMapper objectMapper;
	
	Logger logger = LoggerFactory.getLogger(JsonHelper.class);

	@Autowired
	private RazorpayClient razorpayClient;
	
	@Autowired
	RazorpayConfig razorpayConfig;
	
	public PaymentService(DynamoDB dynamoDB) {
		this.table = dynamoDB.getTable("Payments");
	}

	public PaymentOrder createOrder(double amount, String currency) throws Exception {
		JSONObject orderRequest = new JSONObject();
		orderRequest.put("amount", amount * 100);
		orderRequest.put("currency", currency);
		orderRequest.put("payment_capture", 1);
		Order order = razorpayClient.orders.create(orderRequest);
		System.out.println(order);
		logger.info("Order details: " + order);
		
		PaymentOrder payment = new PaymentOrder();
		payment.setAmount(order.get("amount"));
		payment.setPaymentId(order.get("id"));
		payment.setPaymentStatus("PENDING");
		payment.setCurrency(order.get("currency"));
		logger.info("Saving payment details to DynamoDB: " + payment);
        table.putItem(itemBuilder.buildItem(payment));
		return payment;
	}

	public PaymentOrder updateOrder(PaymentOrder orderDetails) throws Exception {
		Item item = table.getItem("paymentId", orderDetails.getPaymentId());
		if (item == null) {
			throw new RuntimeException("Payment not found");
		}
		item = itemBuilder.buildItem(orderDetails);
		item.with("paymentStatus", "PAID");
		logger.info("Updating payment details to DynamoDB: " + item.toJSON());
		table.putItem(item);
		return jsonHelper.getObjectFromJson(item.toJSON(), PaymentOrder.class);
	}

	public String checkPaymentStatus(String paymentId) throws Exception {
		Item item = table.getItem("paymentId", paymentId);
		if (item == null) {
			throw new RuntimeException("Payment details not found");
		}
		return item.get("paymentStatus").toString();
	}

	public PaymentOrder retrivePaymentDetails(String paymentId) throws Exception {
		Item item = table.getItem("paymentId", paymentId);
		if (item == null) {
			throw new RuntimeException("Payment details not found");
		}
		return jsonHelper.getObjectFromJson(item.toJSON(), PaymentOrder.class);
	}
}