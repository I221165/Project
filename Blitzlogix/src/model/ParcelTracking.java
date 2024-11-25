package model;

import java.time.LocalDateTime;

public class ParcelTracking {
    private int eventID, parcelID;
    private String status;
    private LocalDateTime timestamp;  // this will hold the timestamp for the event

    // Constructor to initialize the parcel tracking info
    public ParcelTracking(int eventID, int parcelID, String status, LocalDateTime timestamp) {
        this.eventID = eventID;
        this.parcelID = parcelID;
        this.status = status;
        this.timestamp = timestamp;
    }

    // Getter methods
    public int getEventID() {
        return eventID;
    }

    public int getParcelID() {
        return parcelID;
    }

    public String getStatus() {
        return status;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    // No need for 'this.timestamp.now()' since timestamp is already a LocalDateTime object
    public LocalDateTime getTime() {
        return this.timestamp;  // simply return the timestamp
    }
}
