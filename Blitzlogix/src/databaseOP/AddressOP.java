package databaseOP;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class AddressOP {

	////////////////////checkers////////////////////////
	
	static int statementChecker(Statement st) {
		if(st == null) {
			return 0;
		} 
		else {
			return 1;
		}
	}
	
	static int connChecker(Connection conn) {
		if(conn == null) {
			return 0;
		} 
		else {
			return 1;
		}
	}

	///////////////////////////////////////////////CRUDs
	
	public static int addressExists(int addressID) {
        Statement st = DatabaseConnection.getStatement();
        Connection conn = DatabaseConnection.getConn();
        if (statementChecker(st) == 0 || connChecker(conn) == 0) {
            return 0;
        }

        try {
            String checkQuery = "SELECT COUNT(*) FROM Address WHERE id = ?";
            PreparedStatement ps = conn.prepareStatement(checkQuery);
            ps.setInt(1, addressID);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                int count = rs.getInt(1);
                if(count > 0)
                	return 1;
                else
                	return 0;
                   
            } else {
                return 0;
            }
        } catch (SQLException e) {
            System.out.println("Error checking address existence: " + e.getMessage());
            return 0;
        }
    }
	
	public static int getAddressID(String city, String neighbourhood, String street, String house) {
        Statement st = DatabaseConnection.getStatement();
        Connection conn = DatabaseConnection.getConn();
        if (statementChecker(st) == 0 || connChecker(conn) == 0) {
            return 0;
        }

        try {
            String selectQuery = "SELECT id FROM Address WHERE city = ? AND neighbourhood = ? AND street = ? AND house = ?";
            PreparedStatement ps = conn.prepareStatement(selectQuery);
            ps.setString(1, city);
            ps.setString(2, neighbourhood);
            ps.setString(3, street);
            ps.setString(4, house);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                int addressID = rs.getInt(1);
                rs.close();
                ps.close();
                return addressID; //to get the id if already exists
            } else {
                rs.close();
                ps.close();
                return 0; 
            }
        } catch (SQLException e) {
            System.out.println("Error fetching address ID: " + e.getMessage());
            return 0; 
        }
    }
	
	
	
	//////////////////////////////////creation////////////////////////////////////
	
	
	public static int createAddress(String city, String neighbourhood, String street, String house) {
        Statement st = DatabaseConnection.getStatement();
        Connection conn = DatabaseConnection.getConn();
        if (statementChecker(st) == 0 || connChecker(conn) == 0) {
            return 0;
        }
        int temp = getAddressID(city,neighbourhood,street,house);
        if(temp != 0) {
        	return temp;
        }

        try {
            String insertQuery = "INSERT INTO Address (city, neighbourhood, street, house) VALUES (?, ?, ?, ?)";
            PreparedStatement ps = conn.prepareStatement(insertQuery);
            ps.setString(1, city);
            ps.setString(2, neighbourhood);
            ps.setString(3, street);
            ps.setString(4, house);

            ps.executeUpdate();

            return getAddressID(city,neighbourhood,street,house); //will return the id of new adddress
        } catch (SQLException e) {
            System.out.println("Error creating address: " + e.getMessage());
            return 0; 
        }
    }
}
