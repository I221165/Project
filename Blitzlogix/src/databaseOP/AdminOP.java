package databaseOP;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class AdminOP {
	
	////////////////////////////////verify login 
	public static boolean verifyAdminLogin(int adminID, String password) {
	    Statement st = DatabaseConnection.getStatement();
	    Connection conn = DatabaseConnection.getConn();
	    if (statementChecker(st) == 0 || connChecker(conn) == 0) {
	        return false;
	    }

	    try {
	        String loginQuery = "SELECT * FROM Admins WHERE AID = ? AND password = ?";
	        PreparedStatement ps = conn.prepareStatement(loginQuery);
	        ps.setInt(1, adminID);
	        ps.setString(2, password);
	        ResultSet rs = ps.executeQuery();

	        if (rs.next()) {
	            rs.close();
	            ps.close();
	            return true; 
	        } else {
	            rs.close();
	            ps.close();
	            return false; 
	        }
	    } catch (SQLException e) {
	        System.out.println("Error verifying admin login: " + e.getMessage());
	        return false;
	    }
	}
	
	
	
	
	///////////////////////////////////////////display managers to delete//////////////////////////
	
	
	public static ResultSet getAllManagers() throws SQLException {
	    Connection conn = DatabaseConnection.getConn();
	    Statement st = conn.createStatement();

	    try {
	        String query = "SELECT * FROM Manager";
	        return st.executeQuery(query);
	    } catch (SQLException e) {
	        System.out.println("Error fetching managers: " + e.getMessage());
	        throw e; // Re-throw the exception to be handled by the caller
	    }
	}
	
	public static ResultSet getAllManagerIDs() throws SQLException {
	    Connection conn = DatabaseConnection.getConn();
	    Statement st = conn.createStatement();

	    try {
	        String query = "SELECT MID FROM Manager";
	        return st.executeQuery(query);
	    } catch (SQLException e) {
	        System.out.println("Error fetching managers: " + e.getMessage());
	        throw e; // Re-throw the exception to be handled by the caller
	    }
	}
	
	
///////////////////////USE BELOW TWO IF NEED ONLY IDS/////////////////////////////////////
	
	
	public static ResultSet getAllDrivers() throws SQLException {
	    Connection conn = DatabaseConnection.getConn();
	    Statement st = conn.createStatement();

	    try {
	        String query = "SELECT * FROM Driver";
	        return st.executeQuery(query);
	    } catch (SQLException e) {
	        System.out.println("Error fetching drivers: " + e.getMessage());
	        throw e; // Re-throw the exception to be handled by the caller
	    }
	}
	
	
	public static ResultSet getAllDriverIDs() throws SQLException {
	    Connection conn = DatabaseConnection.getConn();
	    Statement st = conn.createStatement();

	    try {
	        String query = "SELECT DID FROM Driver";
	        return st.executeQuery(query);
	    } catch (SQLException e) {
	        System.out.println("Error fetching drivers: " + e.getMessage());
	        throw e; // Re-throw the exception to be handled by the caller
	    }
	}
	
	
/////////////////////////////YOU WONT NEED FUNCTIONS BELOW THAT///////////////////////////////////	
	
	
	
	
	
	////////////////////checkers////////////////////////
	
	private static int statementChecker(Statement st) {
		if(st == null) {
			return 0;
		} 
		else {
			return 1;
		}
	}
	
	private static int connChecker(Connection conn) {
		if(conn == null) {
			return 0;
		} 
		else {
			return 1;
		}
	}
	

	
	
	
	
	
	
	
	
	
}
