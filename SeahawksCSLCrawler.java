package com.shengbi.projects.seahawks;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.AmazonSNSClientBuilder;
import com.amazonaws.services.sns.model.MessageAttributeValue;
import com.amazonaws.services.sns.model.PublishRequest;
import com.amazonaws.services.sns.model.PublishResult;

public class SeahawksCSLCrawler implements RequestHandler<Object, Boolean> {

	private static final String PHONE_NUMBER = "<your_phone_number>";
	
	private AmazonSNS snsClient = AmazonSNSClientBuilder.defaultClient();
	private DataHandler dataHandler = new DataHandler();
	
    @Override
    public Boolean handleRequest(Object input, Context context) {

    	List<SeatInfo> affordableSeats = dataHandler.getAffordableSeats();
    	System.out.println("Found affordable seats: " + affordableSeats.size());
    	
    	if (affordableSeats.size() > 0) {
    		sendSMS(affordableSeats);
    	}
    	
      return true;
    }
    
    private void sendSMS(List<SeatInfo> affordableSeats) {
    	
    	String message = affordableSeats.stream()
    			.map(p -> p.section + " " + p.row + " " + p.seats + " => $" + p.pps)
    			.collect(Collectors.joining("; "));
    	Map<String, MessageAttributeValue> smsAttributes = 
                new HashMap<String, MessageAttributeValue>();
    	
    	PublishResult result = snsClient.publish(new PublishRequest()
                .withMessage(message)
                .withPhoneNumber(PHONE_NUMBER)
                .withMessageAttributes(smsAttributes));
    	System.out.println("SMS Result: " + result);
    }

}
