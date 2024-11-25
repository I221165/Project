package model;

public class Route {  //how to handle 2 types?
	private int routeID,sourceID,destinationID; //whenever source and destination are same, use already made routes
          //address not created here
	
	public Route(int routeID, int sourceID, int destinationID) {
        this.routeID = routeID;
        this.sourceID = sourceID;
        this.destinationID = destinationID;
    }
	
	
	
	public int getRouteID() {
		return routeID;
	}

	public void setRouteID(int routeID) {
		this.routeID = routeID;
	}

	public int getSourceID() {
		return sourceID;
	}

	public void setSourceID(int sourceID) {
		this.sourceID = sourceID;
	}

	public int getDestinationID() {
		return destinationID;
	}

	public void setDestinationID(int destinationID) {
		this.destinationID = destinationID;
	}
}   //source and destination IDs are address IDs
