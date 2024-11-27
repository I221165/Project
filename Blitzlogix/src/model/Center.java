package model;


import java.util.ArrayList;

public class Center {
	private int centerID, addressID; //and other obvious stuff like city and others
	
	private ArrayList<Driver> intercityDrivers; //between 2 cities
	private ArrayList<Driver> intracityDrivers; //within city
	
	//make sure not to mixup this 

	//we have selecteed cities where customers can register, each city has a center
	//center has drivers(two array one for intercity and other for 
    private ArrayList<Parcel> parcelIDs = new ArrayList<>(); // will hold stuff when center manager logs in  
    //remain here with status DROPPED_AT_LOCALCENTER and AT_DESTINATION_CENTER untill some driver picks
    //up
    
    
    //if destination city is same as center, show intracity to assign
    //else show intercity to assign and they should come to pickup
     
    
    public Center(int centerID, int addressID) {
        this.centerID = centerID;
        this.addressID = addressID;

        // Initialize driver lists
        intercityDrivers = new ArrayList<>();
        intracityDrivers = new ArrayList<>();

        // Initialize parcel list
        parcelIDs = new ArrayList<>();
    }
    
    
	public int getCenterID() {
		return centerID;
	}

	public void setCenterID(int centerID) {
		this.centerID = centerID;
	}

	public int getAddressID() {
		return addressID;
	}

	public void setAddressID(int addressID) {
		this.addressID = addressID;
	}
	
	
	
	
	
	
	
	
	public void addParcel(Parcel parcel) {
        parcelIDs.add(parcel);
    }

    public void removeParcel(Parcel parcel) {
        parcelIDs.remove(parcel);
    }

    public Parcel searchParcelByID(int parcelID) {
        for (Parcel parcel : parcelIDs) {
            if (parcel.getParcelID() == parcelID) {
                return parcel;
            }
        }
        return null; 
    }
    
    
    
    public void addIntercityDriver(Driver driver) {
        intercityDrivers.add(driver);
    }

    public void removeIntercityDriver(Driver driver) {
        intercityDrivers.remove(driver);
    }

    public Driver searchIntercityDriverByID(int driverID) {
    	for (Driver driver : intercityDrivers) {
            if (driver.getDID() == driverID) {
                return driver;
            }
        }
        return null; 
    }

    // Intracity Driver Management
    public void addIntracityDriver(Driver driver) {
        intracityDrivers.add(driver);
    }

    public void removeIntracityDriver(Driver driver) {
        intracityDrivers.remove(driver);
    }

    public Driver searchIntracityDriverByID(int driverID) {
    	for (Driver driver : intracityDrivers) {
            if (driver.getDID() == driverID) {
                return driver;
            }
        }
        return null; 
    }
	
	
	
	
    public int getNumberOfParcels() {
        return parcelIDs.size();
    }

    public int getNumberOfIntercityDrivers() {
        return intercityDrivers.size();
    }

    public int getNumberOfIntracityDrivers() {
        return intracityDrivers.size();
    }
	
	
	
	
	
	
	
}

 //inventory pubic or private?
