package model;

public class Admin {
	 private int AID;
	 private String password;
     public UserDetails details;
     
    Admin(int aid,String pass,UserDetails Details) {
    	AID = aid;
    	password = pass;
    	details = Details;
    }
     
     
     
	public Admin() {
		// TODO Auto-generated constructor stub
	}




	public int getAID() {
		return AID;
	}
	public void setAID(int aID) {
		AID = aID;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
}
