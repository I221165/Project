package model;

import java.util.List;

public class DriverService {
    private DbHandler dbHandler = new DbHandler();

    public List<String> getAvailableVehiclesByCity(String city) {
        return dbHandler.getAvailableVehiclesByCity(city);
    }

    public boolean registerDriver(String name, String licenseNumber, String city, String vehicleName) {
        return dbHandler.registerDriver(name, licenseNumber, city, vehicleName);
    }
}
