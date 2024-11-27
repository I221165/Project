package databaseOP;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
	
	
	public static int getRouteID(int sourceID, int destinationID) {
	    Statement st = DatabaseConnection.getStatement();
	    Connection conn = DatabaseConnection.getConn();
	    if (statementChecker(st) == 0 || connChecker(conn) == 0) {
	        return 0;
	    }

	    try {
	        String selectQuery = "SELECT route_id FROM Route WHERE source_id = ? AND destination_id = ?";
	        PreparedStatement ps = conn.prepareStatement(selectQuery);
	        ps.setInt(1, sourceID);
	        ps.setInt(2, destinationID);

	        ResultSet rs = ps.executeQuery();

	        if (rs.next()) {
	            int routeID = rs.getInt(1);
	            rs.close();
	            ps.close();
	            return routeID;
	        } else {
	            rs.close();
	            ps.close();
	            return 0; 
	        }
	    } catch (SQLException e) {
	        System.out.println("Error fetching route ID: " + e.getMessage());
	        return -1;
	    }
	}
	

	public static int createRoute(int sourceID, int destinationID) {
        Statement st = DatabaseConnection.getStatement();
        Connection conn = DatabaseConnection.getConn();
        if (statementChecker(st) == 0 || connChecker(conn) == 0 || AddressOP.addressExists(sourceID) == 0 || AddressOP.addressExists(destinationID) == 0) {
            return 0;
        }
        
        int temp = getRouteID(sourceID,destinationID);
        if(temp != 0) {
        	return temp;
        }

        try {
            String insertQuery = "INSERT INTO Route (source_id, destination_id) VALUES (?, ?)";
            PreparedStatement ps = conn.prepareStatement(insertQuery);
            ps.setInt(1, sourceID);
            ps.setInt(2, destinationID);

            ps.executeUpdate();

            return getRouteID(sourceID,destinationID); //only 0 means some issue
            //others are fine
        } catch (SQLException e) {
            System.out.println("Error creating route: " + e.getMessage());
            return 0; // Failure
        }
    }
	
	
	
	////////////////////////get source and destination////////////////////////////////
	
	
	public static int getSourceIDFromRouteID(int routeID) {
	    Statement st = DatabaseConnection.getStatement();
	    Connection conn = DatabaseConnection.getConn();
	    if (statementChecker(st) == 0 || connChecker(conn) == 0) {
	        return 0;
	    }

	    try {
	        String selectQuery = "SELECT source_id FROM Route WHERE route_id = ?";
	        PreparedStatement ps = conn.prepareStatement(selectQuery);
	        ps.setInt(1, routeID);
	        ResultSet rs = ps.executeQuery();

	        if (rs.next()) {
	            int sourceID = rs.getInt(1);
	            rs.close();
	            ps.close();
	            return sourceID;
	        } else {
	            rs.close();
	            ps.close();
	            return 0; // Route not found
	        }
	    } catch (SQLException e) {
	        System.out.println("Error fetching source ID: " + e.getMessage());
	        return -1; // Error occurred
	    }
	}
	
	
	
	public static int getDestinationIDFromRouteID(int routeID) {
	    Statement st = DatabaseConnection.getStatement();
	    Connection conn = DatabaseConnection.getConn();
	    if (statementChecker(st) == 0 || connChecker(conn) == 0) {
	        return 0;
	    }

	    try {
	        String selectQuery = "SELECT destination_id FROM Route WHERE route_id = ?";
	        PreparedStatement ps = conn.prepareStatement(selectQuery);
	        ps.setInt(1, routeID);
	        ResultSet rs = ps.executeQuery();

	        if (rs.next()) {
	            int destinationID = rs.getInt(1);
	            rs.close();
	            ps.close();
	            return destinationID;
	        } else {
	            rs.close();
	            ps.close();
	            return 0; // Route not found
	        }
	    } catch (SQLException e) {
	        System.out.println("Error fetching destination ID: " + e.getMessage());
	        return -1; // Error occurred
	    }
	}
	
	
	
	//////////////////////////get source destination cities////////////////////
	
	
	
	public static String getSourceCityFromRouteID(int routeID) {
	    int sourceID = getSourceIDFromRouteID(routeID);

	    if (sourceID > 0) {
	        return AddressOP.getCityFromAddressID(sourceID);
	    } else {
	        return "Error";
	    }
	}
	
	public static String getDestinationCityFromRouteID(int routeID) {
	    int destinationID = getDestinationIDFromRouteID(routeID);

	    if (destinationID > 0) {
	        return AddressOP.getCityFromAddressID(destinationID);
	    } else {
	        return "Error";
	    }
	}
	
}
