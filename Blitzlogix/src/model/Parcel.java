package model;

public class Parcel {
	   //this will hold the sender and reciever ids
		private int parcelID,senderID,receiverID,weight,routeID,holderID; //routeID will tell source and destination 
		private String status,holder;
		//holder is just an enum class ParcelHolder, available in the same package
		
	//history has our parcel id we dont need that, we can access it with our IDs
		
		
		public Parcel(int parcelID, int senderID, int receiverID, int weight, int routeID, int holderID, String status, String holder) {
	        this.parcelID = parcelID;
	        this.senderID = senderID;
	        this.receiverID = receiverID;
	        this.weight = weight;
	        this.routeID = routeID;
	        this.holderID = holderID;
	        this.status = status;
	        this.holder = holder;
	    }
		
		public Parcel(int parcelID, int senderID, int receiverID, int weight, int routeID, String status, int holderID, String holder) {
	        this.parcelID = parcelID;
	        this.senderID = senderID;
	        this.receiverID = receiverID;
	        this.weight = weight;
	        this.routeID = routeID;
	        this.status = status;
	        this.holder = holder;
	        this.holderID = holderID;
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

	    public int getReceiverID() {
	        return receiverID;
	    }

	    public void setReceiverID(int receiverID) {
	        this.receiverID = receiverID;

	    }

	    public int getWeight() {
	        return weight;
	    }

	    public void setWeight(int weight) {
	        this.weight = weight;
	    }

	    public int getRouteID() {
	        return routeID;
	    }

	    public void setRouteID(int routeID) {
	        this.routeID = routeID;
	    }

	    public int getHolderID() {
	        return holderID;
	    }

	    public void setHolderID(int holderID) {
	        this.holderID = holderID;
	    }

	    public String getStatus() {
	        return status;
	    }

	    public void setStatus(String status) {
	        this.status = status;
	    }

	    public String getHolder() {
	        return holder;
	    }

	    public void setHolder(String holder) {
	        this.holder = holder;

	    }
		
		
}
