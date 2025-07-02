package com.online.delivery.dynamo.factory;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;

public interface DynamoDBClientFactory {
	
	public AmazonDynamoDB getDynamoDBClient();
	public DynamoDB getDynamoDB();

}
