package databaseOP;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class ParcelTrackingOP {
	
	
	/////////////////////////////YOU DONT NEED ANYTHING HERE///////////////////////////
	
	
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
	


	
	
	//do it in every status forward
	public static int updateParcelTracking(int parcelID){
		Connection conn = DatabaseConnection.getConn();
	    Statement st =  DatabaseConnection.getStatement();
	    if (statementChecker(st) == 0 || connChecker(conn) == 0) {
	        return 0;
	    }
	    try {
            String newStatus = ParcelOP.getParcelStatus(parcelID);
	        String insertQuery = "INSERT INTO ParcelTracking (parcelID, status) VALUES (?, ?)";
	        PreparedStatement insertPs = conn.prepareStatement(insertQuery);
	        insertPs.setInt(1, parcelID);
	        insertPs.setString(2, newStatus);
	        insertPs.executeUpdate();
	        return 1;
	    } catch (SQLException e) {
	        System.out.println("Error updating parcel tracking: " + e.getMessage());
	    }
	    
	    return 0;
	}
	
	
}
