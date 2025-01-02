package model;

import java.time.LocalDateTime;

//strategy pattern (different methods)



//just make it with that parcel id and customer id and let it work

public class Payments {    //infact have to fix this 
	
	private PaymentStrategy wayOfPayment;  //cant implement strategy pattern, will need 3 
	//separate tables
	
	LocalDateTime timestamp;  
    private double amount;
    private int parcelID; 
    private int senderID;
    private String name;
    private Boolean done;
    
    private String Bank, transactionID, cardNumber; //set these and call the method afterwards with 
                                                    //strategy name
    
    //a strategy will be given and we will use it
    public Payments(int parcelID) {
    	this.parcelID = parcelID;
        this.done = false;
        this.wayOfPayment = null; 
        timestamp = LocalDateTime.now(); 
    }
    
    
    public Payments( int parcelID, int senderID,double amount, String name) {
        this.amount = amount;
        this.parcelID = parcelID;
        this.senderID = senderID;
        this.name = name;
        this.done = false;
        this.wayOfPayment = null; 
        timestamp = LocalDateTime.now(); 
    }    
    
    
        //payment created without model, paid with model, just need the name and some details
    public void processPayment() { //payment already there, just give method to do
    	if(wayOfPayment != null && done == false) {
    		done = true;
    		timestamp = LocalDateTime.now(); 
    		wayOfPayment.recordPayment(this);
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



	public void setWayOfPayment(String method) {
		PaymentFactory factory = new PaymentFactory();
		wayOfPayment = factory.getWay(method);
	}


	public String getBank() {
		return Bank;
	}


	public void setBank(String bank) {
		Bank = bank;
	}


	public String getTransactionID() {
		return transactionID;
	}


	public void setTransactionID(String transactionID) {
		this.transactionID = transactionID;
	}


	public String getCardNumber() {
		return cardNumber;
	}


	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}
	
    
    //we could will extract sender id to let him know in payments he has to pay for it 
    //but for database we are doing it here too
}
