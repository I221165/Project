package databaseOP;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class FeedbackOP {
	
	
	
	
	public static boolean createFeedback(int parcelID, int writerID, String comment, int score) {
	    Statement st = DatabaseConnection.getStatement();
	    Connection conn = DatabaseConnection.getConn();
	    if (statementChecker(st) == 0 || connChecker(conn) == 0) {
	        return false;
	    }

	    try {
	        String insertQuery = "INSERT INTO Feedback (parcel_id, writer_id, comment, score) VALUES (?, ?, ?, ?)";
	        PreparedStatement insertPs = conn.prepareStatement(insertQuery);
	        insertPs.setInt(1, parcelID);
	        insertPs.setInt(2, writerID);
	        insertPs.setString(3, comment);
	        insertPs.setInt(4, score);
	        insertPs.executeUpdate();

	        return true; 
	    } catch (SQLException e) {
	        System.out.println("Error creating feedback: " + e.getMessage());
	        return false;
	    }
	}
	
	
	
	
	///////////////////////////////////////////////////////////////////////////////////
	
	
	public static ResultSet getFeedbackForManagerCenter(int managerID) {
	    Statement st = DatabaseConnection.getStatement();
	    Connection conn = DatabaseConnection.getConn();
	    if (statementChecker(st) == 0 || connChecker(conn) == 0) {
	        return null;
	    }

	    try {
	        // Get the manager's center ID
	        int centerID = ManagerOP.getCenterIDForManager(managerID);
	        if (centerID == 0) {
	            return null; // Manager not found
	        }
	        String cityName = CenterOP.getCenterCity(centerID);

	        String selectFeedbackQuery = "SELECT f.parcel_id, f.writer_id, s.city AS source_city, d.city AS destination_city, f.comment, f.score " +
	                "FROM Feedback f " +
	                "JOIN Parcel p ON f.parcel_id = p.parcel_id " +
	                "JOIN Route r ON p.route_id = r.route_id " +
	                "JOIN Address s ON r.source_id = s.id " +
	                "JOIN Address d ON r.destination_id = d.id " +
	                "WHERE s.city = ? OR d.city = ?";

	        PreparedStatement selectFeedbackPs = conn.prepareStatement(selectFeedbackQuery);
	        selectFeedbackPs.setString(1, cityName);
	        selectFeedbackPs.setString(2, cityName);
	        ResultSet rs = selectFeedbackPs.executeQuery();

	        return rs;
	    } catch (SQLException e) {
	        System.out.println("Error fetching feedback for manager: " + e.getMessage());
	        return null;
	    }
	}
	
	
	
	
	
	
	//////////////////////////////////////////////////////////////////////////////////////
	
	
	
	
	
	public static ResultSet getParcelsForFeedback(int customerID) {
	    Statement st = DatabaseConnection.getStatement();
	    Connection conn = DatabaseConnection.getConn();
	    if (statementChecker(st) == 0 || connChecker(conn) == 0) {
	        return null;
	    }

	    try {
	        String selectQuery = "SELECT p.parcel_id, s.city AS source_city, d.city AS destination_city, " +
	                "s_cust.name AS sender_name, r_cust.name AS receiver_name " +
	                "FROM Parcel p " +
	                "JOIN Route r ON p.route_id = r.route_id " +
	                "JOIN Address s ON r.source_id = s.id " +
	                "JOIN Address d ON r.destination_id = d.id " +
	                "JOIN Customer s_cust ON p.sender_id = s_cust.CID " +
	                "JOIN Customer r_cust ON p.receiver_id = r_cust.CID " +
	                "WHERE (p.sender_id = ? OR p.receiver_id = ?)";

	        PreparedStatement ps = conn.prepareStatement(selectQuery);
	        ps.setInt(1, customerID);
	        ps.setInt(2, customerID);
	        ResultSet rs = ps.executeQuery();

	        return rs;
	    } catch (SQLException e) {
	        System.out.println("Error fetching parcels for feedback: " + e.getMessage());
	        return null;
	    }
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	////////////////////////////////////////just for ids
	
	
	public static ResultSet getParcelIDsForFeedback(int customerID) {
	    Statement st = DatabaseConnection.getStatement();
	    Connection conn = DatabaseConnection.getConn();
	    if (statementChecker(st) == 0 || connChecker(conn) == 0) {
	        return null;
	    }

	    try {
	        String selectQuery = "SELECT p.parcel_id " +
	                "FROM Parcel p " +
	                "WHERE (p.sender_id = ? OR p.receiver_id = ?) AND p.status = 'DELIVERED'";

	        PreparedStatement ps = conn.prepareStatement(selectQuery);
	        ps.setInt(1, customerID);
	        ps.setInt(2, customerID);
	        ResultSet rs = ps.executeQuery();

	        return rs;
	    } catch (SQLException e) {
	        System.out.println("Error fetching parcel IDs for feedback: " + e.getMessage());
	        return null;
	    }
	}
	
	
	
	
	
	
	

	
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
	
	
	

	
	
	
	
	
	
}
