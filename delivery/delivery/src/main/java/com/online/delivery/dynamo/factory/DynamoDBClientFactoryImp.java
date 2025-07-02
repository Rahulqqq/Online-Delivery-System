package com.online.delivery.dynamo.factory;

import java.util.ArrayList;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.local.embedded.DynamoDBEmbedded;
import com.amazonaws.services.dynamodbv2.model.AttributeDefinition;
import com.amazonaws.services.dynamodbv2.model.CreateTableRequest;
import com.amazonaws.services.dynamodbv2.model.KeySchemaElement;
import com.amazonaws.services.dynamodbv2.model.KeyType;
import com.amazonaws.services.dynamodbv2.model.ProvisionedThroughput;

@Configuration
public class DynamoDBClientFactoryImp implements DynamoDBClientFactory {

	@Override
	public AmazonDynamoDB getDynamoDBClient() {
		AmazonDynamoDB amazonDynamoDBClient = DynamoDBEmbedded.create().amazonDynamoDB();
		System.out.println("Created amazonDynamoDBClient");
		return amazonDynamoDBClient;
	}

	@Override
	@Bean
	public DynamoDB getDynamoDB() {
		DynamoDB dynamoDB = new DynamoDB(getDynamoDBClient());
		System.out.println("Created dynamoDB instance");
		createTable(dynamoDB);
		System.out.println("Products table created");
		return dynamoDB;
	}
	
	private void createTable(DynamoDB dynamoDB) {
		ArrayList<KeySchemaElement> keySchema = new ArrayList<>();
		keySchema.add(new KeySchemaElement().withAttributeName("productId").withKeyType(KeyType.HASH));
		
		ArrayList<AttributeDefinition> attributeDefinitions = new ArrayList<>();
		attributeDefinitions.add(new AttributeDefinition().withAttributeName("productId").withAttributeType("S"));
		
		CreateTableRequest request = new CreateTableRequest()
				.withTableName("Products")
				.withKeySchema(keySchema)
				.withAttributeDefinitions(attributeDefinitions)
				.withProvisionedThroughput(new ProvisionedThroughput()
						.withReadCapacityUnits(5L)
						.withWriteCapacityUnits(5L));
		dynamoDB.createTable(request);
	}

}
