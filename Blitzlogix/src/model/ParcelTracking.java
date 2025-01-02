package model;

import java.time.LocalDateTime;

public class ParcelTracking {

    private int eventID;       // Unique event identifier
    private int parcelID;      // Parcel ID
    private String status;     // Status of the parcel (e.g., Delivered, In Transit)
    private LocalDateTime timestamp;  // Timestamp of the event

    // Constructor
    public ParcelTracking(int eventID, int parcelID, String status, LocalDateTime timestamp) {
        this.eventID = eventID;
        this.parcelID = parcelID;
        this.status = status;
        this.timestamp = timestamp;
    }

    // Getters and Setters
    public int getEventID() {
        return eventID;
    }

    public void setEventID(int eventID) {
        this.eventID = eventID;
    }

    public int getParcelID() {
        return parcelID;
    }

    public void setParcelID(int parcelID) {
        this.parcelID = parcelID;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "ParcelTracking{" +
                "eventID=" + eventID +
                ", parcelID=" + parcelID +
                ", status='" + status + '\'' +
                ", timestamp=" + timestamp +
                '}';
    }
}
