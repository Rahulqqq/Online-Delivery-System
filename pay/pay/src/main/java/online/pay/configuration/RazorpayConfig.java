package online.pay.configuration;



import com.razorpay.RazorpayClient;

import lombok.Getter;
import lombok.Setter;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Getter
@Setter
public class RazorpayConfig {
	@Value("${razorpay.keyId}")
	private String keyId;
	@Value("${razorpay.keySecret}")
	private String keySecret;

	@Bean
	public RazorpayClient razorpayClient() throws Exception {
		return new RazorpayClient(keyId, keySecret);
	}
}
