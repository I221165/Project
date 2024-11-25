package databaseOP;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class RouteOP {
	
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
	
	
	////////////////////////////////////////////CRUDs

	public static int createRoute(int sourceID, int destinationID) {
        Statement st = DatabaseConnection.getStatement();
        Connection conn = DatabaseConnection.getConn();
        if (statementChecker(st) == 0 || connChecker(conn) == 0 || AddressOP.addressExists(sourceID) == 0 || AddressOP.addressExists(destinationID) == 0) {
            return 0;
        }

        try {
            String insertQuery = "INSERT INTO Route (source_id, destination_id) VALUES (?, ?)";
            PreparedStatement ps = conn.prepareStatement(insertQuery);
            ps.setInt(1, sourceID);
            ps.setInt(2, destinationID);

            ps.executeUpdate();

            return 1; // Route created successfully
        } catch (SQLException e) {
            System.out.println("Error creating route: " + e.getMessage());
            return 0; // Failure
        }
    }
	
	
	
	////////////////////////////////////////////////////////////////////
	
	
}
