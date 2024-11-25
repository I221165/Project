package controller;

public class Session {
    private static Session instance;
    private String userId;
    private String driverId;
    private String adminId;

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
    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }

    public String getDriverId() { return driverId; }
    public void setDriverId(String driverId) { this.driverId = driverId; }

    public String getAdminId() { return adminId; }
    public void setAdminId(String adminId) { this.adminId = adminId; }

    // Clear all session data
    public void clearSession() {
        this.userId = null;
        this.driverId = null;
        this.adminId = null;
    }
}
