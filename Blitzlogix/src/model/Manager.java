package model;

public class Manager { //just remember to toggle of the status when making an entry with same CNIC
	private int MID;
	public UserDetails details;
	private String password;
	private int centerID;
	
	public Manager(int MID, UserDetails details, String password, int centerID) {
        this.MID = MID;
        this.details = details;
        this.password = password;
        this.centerID = centerID;                                
	}
	
	
	public int getMID() {
		return MID;
	}
	public void setMID(int mID) {
		MID = mID;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public int getCenterID() {
		return centerID;
	}
	public void setCenterID(int centerID) {
		this.centerID = centerID;
	}
	
	//jobstatus to keep data of previous stuff
	// primary key will be MID+centerID, so if reassigned to a new location,data remains
}  
