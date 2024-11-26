package controller;

public class Session {
    private static Session instance;
    private int userId;
    private int driverId;
    private int adminId;
    private int parcelID;
    private int ManagerID;

    private Session() {
        // Private constructor
    }

    public static Session getInstance() {
        if (instance == null) {
            instance = new Session();
        }
        return instance;
    }

    // Getters and Setters
    public int getUserId() { return userId; }
    public void setUserId(int userId2) { this.userId = userId2; }

    public int getDriverId() { return driverId; }
    public void setDriverId(int driverId) { this.driverId = driverId; }

    public int getAdminId() { return adminId; }
    public void setAdminId(int adminId) { this.adminId = adminId; }

    public int getParcelID() { return parcelID; }
    public void setParcelID(int parcelID) { this.parcelID = parcelID; }
    
    
    public int getManagerID() { return ManagerID; }
    public void setManagerID(int ManagerID) { this.ManagerID = ManagerID; }
    // Clear all session data
    public void clearSession() {
        this.userId = 0;
        this.driverId = 0;
        this.adminId = 0;
        parcelID = 0;
        ManagerID=0;
    }
}
