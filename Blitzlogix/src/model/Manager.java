package model;

public class Manager {
	public int MID;
	public UserDetails details;
	
	
	public Boolean job_status;  //whether active or not (can be removed but we keep his userdetails)
	//after that we see ka whats the center
	int centerID; //  -1 if off job
	
	//jobstatus to keep data of previous stuff
	// primary key will be MID+centerID, so if reassigned to a new location,data remains
}  
