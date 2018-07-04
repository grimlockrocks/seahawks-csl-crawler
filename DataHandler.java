package com.shengbi.projects.seahawks;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

public class DataHandler {
	
	private static final String URL = "https://seahawks.strmarketplace.com/WebServices/json/F021F1B/data.json";
	
	private static final List<String> FAVORITE_SECTIONS = Arrays.asList(
			"CHR138", "CHR137", "CHR136", "CHR135", "CHR134", "CHR133", "CHR132",  
			"CHR332", "CHR333", "CHR334", "CHR335", "CHR336", "CHR337", "CHR338");
	private static final int HIGHEST_PRICE_THRESHOLD = 10000;
	private static final int QUANTITY = 2;
	
	public List<SeatInfo> getAvailableSeats() {
		
		ObjectMapper mapper = new ObjectMapper();
		mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
		mapper.configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true);
		try {
			List<SeatInfo> data = mapper.readValue(new URL(URL), new TypeReference<List<SeatInfo>>(){ });
			return data;
		} catch (IOException ex) {
			System.err.print("Error: " + ex.getMessage());
			return new ArrayList<SeatInfo>();
		}
	}
	
	public List<SeatInfo> getAffordableSeats() {
		
		List<SeatInfo> allSeats = getAvailableSeats();
		List<SeatInfo> affordableSeats = allSeats.stream()
				.filter(p -> isAffordable(p))
				.collect(Collectors.toList());
		return affordableSeats;
	}
	
	private static boolean isAffordable(SeatInfo seat) {
		
		return (QUANTITY == seat.quantity || QUANTITY == seat.sellerWillingToSellTickets)
				&& HIGHEST_PRICE_THRESHOLD >= seat.pps && FAVORITE_SECTIONS.contains(seat.section);
	}
}
