package model;

public class UserDetails {
        //basic variables and this will work for common methods like login and details updation for all 
	private String CNIC,name,phone,email;
	
	public UserDetails(String cnic,String Name,String Phone,String Email) {
		this.CNIC = cnic;
		this.name = Name;
		this.phone = Phone;
		this.email = Email;
	}
	   public UserDetails() {
		// TODO Auto-generated constructor stub
	}
	//CNIC to match the details
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getCNIC() {
		return CNIC;
	}
	public void setCNIC(String cNIC) {
		CNIC = cNIC;
	}
}



//if we try to enter a CNIC twice(for instance a customer is also our employee, user details wont be
//duplicated, in both tables customer and driver/manager they will have same userdetails id)