package model;

import java.time.LocalDateTime;

//strategy pattern (different methods)


public class Payments {    //infact have to fix this 
	
	private PaymentStrategy wayOfPayment;  //cant implement strategy pattern, will need 3 
	//separate tables
	
	LocalDateTime timestamp;  
    private double amount;
    private int parcelID; 
    private int senderID;
    private String name;
    private Boolean done;
    
    //a strategy will be given and we will use it
    public Payments(double amount, int parcelID, int senderID, String name) {
        this.amount = amount;
        this.parcelID = parcelID;
        this.senderID = senderID;
        this.name = name;
        this.done = false;
        this.wayOfPayment = null; // Set payment strategy to null
    }

    // Constructor with timestamp
    public Payments(double amount, int parcelID, int senderID, String name, LocalDateTime timestamp) {
        this.amount = amount;
        this.parcelID = parcelID;
        this.senderID = senderID;
        this.name = name;
        this.done = false;
        this.timestamp = timestamp;
        this.wayOfPayment = null; // Set payment strategy to null
    }
    
    
    
    public void processPayment() { //completing it for the first time
    	if(wayOfPayment != null && done == false) {
    		done = true;
    		timestamp = LocalDateTime.now(); //payment just done
    		wayOfPayment.recordPayment(done,timestamp);
    	}
    }
    
    
    
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	public int getParcelID() {
		return parcelID;
	}
	public void setParcelID(int parcelID) {
		this.parcelID = parcelID;
	}
	public int getSenderID() {
		return senderID;
	}
	public void setSenderID(int senderID) {
		this.senderID = senderID;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public void recordPayment() {
		//record stuff
	}



	public Boolean getDone() {
		return done;
	}



	public void setDone(Boolean done) { //maybe add the strategy here
		this.done = done;
	}



	public PaymentStrategy getWayOfPayment() {
		return wayOfPayment;
	}



	public void setWayOfPayment(PaymentStrategy wayOfPayment) {
		this.wayOfPayment = wayOfPayment;
	}
	
    
    //we could will extract sender id to let him know in payments he has to pay for it 
    //but for database we are doing it here too
}
