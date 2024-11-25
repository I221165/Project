package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DbHandler {

	public boolean registerCustomer(Customer customer) {
        String query = "INSERT INTO public.customers (username, password, email) VALUES (?, ?, ?)";
        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, customer.getDetails().getName());
            pstmt.setString(2, customer.getPassword());
            pstmt.setString(3, customer.getDetails().getEmail());
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    

    public boolean customerExists(String username, String password) {
        String query = "SELECT * FROM public.customers WHERE username = ? AND password = ?";
        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, username);
            pstmt.setString(2, password);
            ResultSet rs = pstmt.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean userExists(String username, String email) {
        String query = "SELECT * FROM public.users WHERE username = ? OR email = ?";
        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, username);
            pstmt.setString(2, email);
            ResultSet rs = pstmt.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    
    
    public boolean verifyAdmin(int i, String password) {
        String query = "SELECT * FROM" + " public.admin"+" WHERE id = ? AND password = ?";
        
        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
             
            pstmt.setInt(1, i);
            pstmt.setString(2, password);
            ResultSet rs = pstmt.executeQuery();
            
            return rs.next();  
            
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    
    public List<String> getAvailableVehiclesByCity(String city) {
        List<String> vehicles = new ArrayList<>();
        String query = "SELECT vehicle_name FROM"+" public.vehicles WHERE city = ? AND is_available = TRUE";
        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, city);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                vehicles.add(rs.getString("vehicle_name"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return vehicles;
    }

    
    
    public boolean registerDriver(String name, String licenseNumber, String city, String vehicleName) {
        String driverQuery = "INSERT INTO public.delivery_person (name, license_number, city, assigned_vehicle, status) VALUES (?, ?, ?, ?, 'Active')";
        String vehicleUpdateQuery = "UPDATE public.vehicles SET is_available = FALSE WHERE vehicle_name = ? AND city = ?";

        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement driverStmt = conn.prepareStatement(driverQuery);
             PreparedStatement vehicleStmt = conn.prepareStatement(vehicleUpdateQuery)) {

            conn.setAutoCommit(false);  

            driverStmt.setString(1, name);
            driverStmt.setString(2, licenseNumber);
            driverStmt.setString(3, city);
            driverStmt.setString(4, vehicleName);
            driverStmt.executeUpdate();

            vehicleStmt.setString(1, vehicleName);
            vehicleStmt.setString(2, city);
            vehicleStmt.executeUpdate();

            conn.commit();  
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
}
