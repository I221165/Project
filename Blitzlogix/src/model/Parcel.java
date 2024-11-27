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
		
		public Parcel(int parcelID2, int senderID2, int receiverID2, int weight2, int routeID2, String string, int i,
				String holder2) {
			
			this.parcelID = parcelID2;
	        this.senderID = senderID2;
	        this.receiverID = receiverID2;
	        this.weight = weight2;
	        this.routeID = routeID2;
	        this.holderID = i;
	        this.status = string;
	        this.holder = holder2;
			
			// TODO Auto-generated constructor stub
		}

		public Parcel(int parcelID2, String status2) {
			// TODO Auto-generated constructor stub
			this.parcelID = parcelID2;
			this.status = status2;
		}

		public Parcel() {
			this.parcelID = 0;
	        this.senderID = 0;
	        this.receiverID = 0;
	        this.weight = 0;
	        this.routeID = 0;
	        this.holderID = 0;
	        this.status = "";
	        this.holder = "";
			// TODO Auto-generated constructor stub
			
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
