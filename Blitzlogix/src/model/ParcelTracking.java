package model;

import java.time.LocalDateTime;

//to display in parcel tracking

public class ParcelTracking {   //important for tracking, different events recorded here
	
	public ParcelTracking(int i, int j, String string, LocalDateTime minusDays) {
		// TODO Auto-generated constructor stub
		this.eventID = i;
		this.parcelID = j;
		status = string;
		
	}
	int eventID,parcelID; //eventID just for the primary key
	String status; //enum is just to get exact names everytime
	LocalDateTime timestamp;  
	
	//just do localdatetime.now() to get the date
}


///its just history of what event happened to what parcel at what time
