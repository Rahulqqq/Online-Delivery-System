package online.pay.utils;

import java.util.UUID;

import org.springframework.stereotype.Component;

@Component
public class IdGenerator {
	
	public String generateId() {
		return UUID.randomUUID().toString();
	}
	
	public String buildCompositeKey(String firstHalf, String secondHalf) {
		return firstHalf + "|" +secondHalf;
	}

}
