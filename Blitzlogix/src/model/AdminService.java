package model;


public class AdminService {
    private DbHandler dbHandler = new DbHandler();

    public boolean loginAdmin(Admin admin) {
        return dbHandler.verifyAdmin(admin.getAID(), admin.getPassword());
    }
}
