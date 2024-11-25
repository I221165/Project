package databaseOP;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DriverOP {

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
	
	
	public static int searchDriver(int driverID) {
		 Statement st = DatabaseConnection.getStatement();
		    Connection conn = DatabaseConnection.getConn();
		    if (statementChecker(st) == 0 || connChecker(conn) == 0) {
		        return 0;
		    }


	    try {
	        String checkQuery = "SELECT ID FROM Driver WHERE ID = ?";
	        PreparedStatement ps = conn.prepareStatement(checkQuery);
	        ps.setInt(1, driverID);
	        ResultSet rs = ps.executeQuery();

	        if (rs.next()) {
	            int id = rs.getInt(1); 
	            rs.close();
	            ps.close();
	            return id; // Driver found, return ID
	        } else {
	            rs.close();
	            ps.close();
	            return 0; // Driver not found
	        }
	    } catch (SQLException e) {
	        System.out.println("Error searching driver: " + e.getMessage());
	        return 0;
	    }
	}
	
	
	public static int searchDriver(String cnic) {
		Statement st = DatabaseConnection.getStatement();
	    Connection conn = DatabaseConnection.getConn();
	    if (statementChecker(st) == 0 || connChecker(conn) == 0) {
	        return 0;
	    }


	    try {
	        String checkQuery = "SELECT DID FROM Driver WHERE CNIC = ?";
	        PreparedStatement ps = conn.prepareStatement(checkQuery);
	        ps.setString(1, cnic);
	        ResultSet rs = ps.executeQuery();

	        if (rs.next()) {
	            int driverID = rs.getInt(1);
	            rs.close();
	            ps.close();
	            return driverID;
	        } else {
	            rs.close();
	            ps.close();
	            return 0; 
	        }
	    } catch (SQLException e) {
	        System.out.println("Error searching driver: " + e.getMessage());
	        return 0;
	    }
	}
	
	
	
	/////////////////////////////////////CRUDS/////////////////////////////
	
	
	public static int registerDriver(String cnic, String name, String phone, String email, String password, String driverType, int homeCenter) {
	    Statement st = DatabaseConnection.getStatement();
	    Connection conn = DatabaseConnection.getConn();
	    if (statementChecker(st) == 0 || connChecker(conn) == 0) {
	        return 0;
	    }

	    try {
	        // Check if the user (with the same CNIC) already exists
	    	if(searchDriver(cnic) != 0) { //some driver with same cnic exists
	    		return 0;
	    	}
	    	
	    	UserOP.makeUser(cnic, name, phone, email);//we dont care if it already exists or not
	    	                                          //we just happy its there
	    	

	        String insertQuery = "INSERT INTO Driver (password, cnic, driver_type, homeCenter, currentCenter) VALUES (?, ?, ?, ?, ?)";
	        PreparedStatement insertPs = conn.prepareStatement(insertQuery);
	        insertPs.setString(1, password);
	        insertPs.setString(2, cnic);
	        insertPs.setString(3, driverType);
	        insertPs.setInt(4, homeCenter);
	        insertPs.setInt(5, homeCenter); //both same at creation
	        insertPs.executeUpdate();

	        return 1;
	    } catch (SQLException e) {
	        System.out.println("Error saving driver data in database: " + e.getMessage());
	        return 0; 
	    }
	}
	
	
	
	
	//cannot have all kinds of checks, would be tooooo hectic
	//having some, will try to code carefully
	public static int changeDriverCurrentCenter(int driverID, int newCenterID) {
	    Statement st = DatabaseConnection.getStatement();
	    Connection conn = DatabaseConnection.getConn();
	    if (statementChecker(st) == 0 || connChecker(conn) == 0) {
	        return 0;
	    }

	    try {
	        String updateQuery = "UPDATE Driver SET currentCenter = ? WHERE DID = ?";
	        PreparedStatement ps = conn.prepareStatement(updateQuery);
	        ps.setInt(1, newCenterID);
	        ps.setInt(2, driverID);
	        ps.executeUpdate();

	        return 1;
	    } catch (SQLException e) {
	        System.out.println("Error updating driver's current center: " + e.getMessage());
	        return 0;
	    }
	}
	
	
	
	public static int incrementDriverParcelsDelivered(int driverID) {
		Statement st = DatabaseConnection.getStatement();
	    Connection conn = DatabaseConnection.getConn();
	    if (statementChecker(st) == 0 || connChecker(conn) == 0) {
	        return 0;
	    }

	    try {
	        String updateQuery = "UPDATE Driver SET parcelsDelivered = parcelsDelivered + 1 WHERE DID = ?";
	        PreparedStatement ps = conn.prepareStatement(updateQuery);
	        ps.setInt(1, driverID);
	        ps.executeUpdate();

	        return 1;
	    } catch (SQLException e) {
	        System.out.println("Error updating driver's parcels delivered: " + e.getMessage());
	        return 0;
	    }
	}
	
	
	
	
	
	
	//to change this status
	public static int toggleDriverOutForDelivery(int driverID) {
		Statement st = DatabaseConnection.getStatement();
	    Connection conn = DatabaseConnection.getConn();
	    if (statementChecker(st) == 0 || connChecker(conn) == 0) {
	        return 0;
	    }

	    try {
	        String updateQuery = "UPDATE Driver SET outForDelivery = NOT outForDelivery WHERE DID = ?";
	        PreparedStatement ps = conn.prepareStatement(updateQuery);
	        ps.setInt(1, driverID);
	        ps.executeUpdate();

	        return 1;
	    } catch (SQLException e) {
	        System.out.println("Error toggling driver's outForDelivery status: " + e.getMessage());
	        return 0;
	    }
	}
	
	
	
	
	/////////////////keep driver removal conditions in mind////////////
	
	
}
