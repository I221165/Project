package databaseOP;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class CenterOP {

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
	
	
   ///////////////////////////////searchers(operator overloading)/////////
	
	
	public static int centerExists(int centerID) {
        Statement st = DatabaseConnection.getStatement();
        Connection conn = DatabaseConnection.getConn();
        if (statementChecker(st) == 0 || connChecker(conn) == 0) {
            return 0;
        }

        try {
            String checkQuery = "SELECT COUNT(*) FROM Center WHERE center_id = ?";
            PreparedStatement ps = conn.prepareStatement(checkQuery);
            ps.setInt(1, centerID);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                int count = rs.getInt(1);
                if(count > 0 )
                	return 1;
                else 
                	return 0;
            } else {
                return 0;
            }
        } catch (SQLException e) {
            System.out.println("Error checking center existence: " + e.getMessage());
            return 0;
        }
    }
	
	
	
	
	////////////////////////////getters//////////////////////////////////////////////////
	
	public static String getCenterCity(int centerID) {
        Statement st = DatabaseConnection.getStatement();
        Connection conn = DatabaseConnection.getConn();
        if (statementChecker(st) == 0 || connChecker(conn) == 0) {
            return null;
        }

        try {
            // Assuming a foreign key relationship between Center and Address tables
            String selectQuery =
                    "SELECT a.city FROM Center c JOIN Address a ON c.address_id = a.id WHERE c.center_id = ?";
            PreparedStatement ps = conn.prepareStatement(selectQuery);
            ps.setInt(1, centerID);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                String city = rs.getString("city");
                return city;
            } else {
                return null; // Center not found
            }
        } catch (SQLException e) {
            System.out.println("Error fetching center city: " + e.getMessage());
            return null;
        }
    }
	
	
	
	
	
	
	
	
	
	
	
	///////////////////////////////////////////get the center of the city 
	
	public static int getCenterIDByCity(String city) {
	    Statement st = DatabaseConnection.getStatement();
	    Connection conn = DatabaseConnection.getConn();
	    if (statementChecker(st) == 0 || connChecker(conn) == 0) {
	        return -1; // Indicate error or no connection
	    }

	    try {
	        // Assuming a foreign key relationship between Center and Address tables
	        String selectQuery = "SELECT c.center_id FROM Center c JOIN Address a ON c.address_id = a.id WHERE a.city = ?";
	        PreparedStatement ps = conn.prepareStatement(selectQuery);
	        ps.setString(1, city);
	        ResultSet rs = ps.executeQuery();

	        if (rs.next()) {
	            return rs.getInt("center_id");
	        } else {
	            return 0; // No center found for the city
	        }
	    } catch (SQLException e) {
	        System.out.println("Error fetching center ID by city: " + e.getMessage());
	        return -1; // Indicate error
	    }
	}
	
	
	
	
}
