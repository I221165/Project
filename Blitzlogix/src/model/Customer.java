package model;

public class Customer {
	
	
	private int CID;
	private String password; //to prevent an extra query in database and passwords are linked to accounts not CNICs
	public UserDetails details;
	      //in database we have CNIC in every table to link to these data values
	private int moneySpent,totalParcels; //include only sent //from all those parcels   (whats going to be the rate?)
	
	Customer(int cid,String pass,UserDetails Details) {
		
		CID = cid;
		password = pass;
		details = Details;
		moneySpent = 0;
		totalParcels = 0;
	}
	
	
	public Customer() {
		CID = 0;
		password = "";
		details = null;
		moneySpent = 0;
		totalParcels = 0;
		// TODO Auto-generated constructor stub
	}


	public void incrementTotalParcels() {
        totalParcels++;
    }

    // Add to the total money spent
    public void addToMoneySpent(int amount) {
        moneySpent += amount;
    }
	
	
	
	public int getMoneySpent() {
		return moneySpent;
	}
	public void setMoneySpent(int moneySpent) {
		this.moneySpent = moneySpent;
	}
	public int getCID() {
		return CID;
	}
	public void setCID(int cID) {
		CID = cID;
	}
	public int getTotalParcels() {
		return totalParcels;
	}
	public void setTotalParcels(int totalParcels) {
		this.totalParcels = totalParcels;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}


	

	public UserDetails getDetails() {
		// TODO Auto-generated method stub
		
		return this.details;
	}
}
