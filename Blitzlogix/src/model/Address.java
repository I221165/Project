package model;

public class Address {
    private int id; // Unique ID for the address
    private String city;
    private String neighbourhood;
    private String streetNumber;
    private String houseNumber;

    // Constructor with id
    public Address(int id, String city, String neighbourhood, String streetNumber, String houseNumber) {
        this.id = id;
        this.city = city;
        this.neighbourhood = neighbourhood;
        this.streetNumber = streetNumber;
        this.houseNumber = houseNumber;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getNeighbourhood() {
        return neighbourhood;
    }

    public void setNeighbourhood(String neighbourhood) {
        this.neighbourhood = neighbourhood;
    }

    public String getStreetNumber() {
        return streetNumber;
    }

    public void setStreetNumber(String streetNumber) {
        this.streetNumber = streetNumber;
    }

    public String getHouseNumber() {
        return houseNumber;
    }

    public void setHouseNumber(String houseNumber) {
        this.houseNumber = houseNumber;
    }

    @Override
    public String toString() {
        return houseNumber + ", " + streetNumber + ", " + neighbourhood + ", " + city;
    }

    
}
