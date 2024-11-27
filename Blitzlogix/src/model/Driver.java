package model;

import java.util.ArrayList;

public class Driver {
	private int DID;
	private String password;
	private String Dtype; //  use the enum class DriverType here
	public UserDetails details;
	private int parcelsDelivered; //total
    private int currentCenterID; //first assigned to a center 
    
    
    //exactly like parcels for inventory, here if centerID is -1, its between city transit(has picked up not dropped yet)
    //intracity drivers will always have these values same

    
    
	private Boolean outForDelivery; 
	
	//if its true, wont be showed to center manager to assign any parcel
	//for intracity will only be true if driver dropoffs at a client and client hasnt recievd
	//for intercity will be true if picks up some packages for another city, and eventuall leaves the center array
	
	
	
	ArrayList<Parcel> parcelIDs;
    //to be or not to be 

	
	public int getDID() {
		return DID;
	}
	public void setDID(int dID) {
		DID = dID;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Boolean getOutForDelivery() {
		return outForDelivery;
	}
	public void setOutForDelivery(Boolean outForDelivery) {
		this.outForDelivery = outForDelivery;
	}
	public String getDtype() {
		return Dtype;
	}
	public void setDtype(String dtype) {
		Dtype = dtype;
	}
	public int getCurrentCenterID() {
		return currentCenterID;
	}
	public void setCurrentCenterID(int centerID) {
		this.currentCenterID = centerID;
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

    public int getNumberOfParcels() {
        return parcelIDs.size();
    }
	public int getParcelsDelivered() {
		return parcelsDelivered;
	}
	public void setParcelsDelivered(int parcelsDelivered) {
		this.parcelsDelivered = parcelsDelivered;
	}
	
	
	public void incrementParcelsDelivered() {
        parcelsDelivered++;
    }
	
	
}
