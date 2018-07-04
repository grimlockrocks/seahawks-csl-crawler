package com.shengbi.projects.seahawks;

public class SeatInfo {

	public int id;
	public int aisle;
	public int pps;
	public int quantity;
	public int sellerWillingToSellTickets;
	public String section;
	public String row;
	public String seats;
	
	public String toString() { 
	    return "Id: " + this.id + 
	    		", Aisle: " + (this.aisle == 1 ? "Yes" : "No") + 
	    		", Price: $" + this.pps +
	    		", Quantity: " + this.quantity +
	    		", SellerWillingToSellTickets: " + this.sellerWillingToSellTickets + 
	    		", Section: " + this.section + 
	    		", Row: " + this.row +
	    		", Seats: " + this.seats;
	} 
}
