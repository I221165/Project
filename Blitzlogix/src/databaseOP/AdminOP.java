package databaseOP;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class AdminOP {
	
	public static ResultSet getAllCustomers(){

	    Statement st = DatabaseConnection.getStatement();

	    Connection conn = DatabaseConnection.getConn();



	    if (statementChecker(st) == 0 || connChecker(conn) == 0) {

	        return null;

	    }



	    try {

	        String selectQuery = "SELECT name, phone, total_parcels, email, money_spent FROM UserDetails JOIN Customer ON UserDetails.CNIC = Customer.CNIC";

	        PreparedStatement ps = conn.prepareStatement(selectQuery);

	        ResultSet rs = ps.executeQuery();



	        return rs;

	    } catch (SQLException e) {

	        System.out.println("Error fetching customer records: " + e.getMessage());

	        return null;

	    }

	}
	
	public static ResultSet getAllDrivers2(){

	    Statement st = DatabaseConnection.getStatement();

	    Connection conn = DatabaseConnection.getConn();



	    if (statementChecker(st) == 0 || connChecker(conn) == 0) {

	        return null;

	    }



	    try {

	        String selectQuery = "SELECT u.name, c.city AS currentCenter, d.phone, d.email, d.parcelsDelivered " +

	                             "FROM Driver d " +

	                             "JOIN UserDetails u ON d.CNIC = u.CNIC " +

	                             "JOIN Center c ON d.currentCenter = c.centerID";



	        PreparedStatement ps = conn.prepareStatement(selectQuery);

	        ResultSet rs = ps.executeQuery();



	        return rs;

	    } catch (SQLException e) {

	        System.out.println("Error fetching driver records: " + e.getMessage());

	        return null;

	    } 

	}
	
	public static int updateCustomer(int customerID, String name, String cnic, String email, String phone, String password) {

	    Statement st = DatabaseConnection.getStatement();

	    Connection conn = DatabaseConnection.getConn();



	    if (statementChecker(st) == 0 || connChecker(conn) == 0) {

	        return 0; 

	    }



	    try {



	        if (cnic != null && !cnic.isEmpty()) {

	            String checkQuery = "SELECT COUNT(*) FROM Customer WHERE CNIC = ? AND CID != ?";

	            PreparedStatement checkPs = conn.prepareStatement(checkQuery);

	            checkPs.setString(1, cnic);

	            checkPs.setInt(2, customerID);

	            ResultSet rs = checkPs.executeQuery();



	            if (rs.next() && rs.getInt(1) > 0) {

	                System.out.println("CNIC already exists for another customer");

	                return 0;

	            }

	        }



	        String updateQuery = "UPDATE Customer SET ";

	        boolean firstUpdate = true;



	        if (name != null && !name.isEmpty()) {

	            if (!firstUpdate) {

	                updateQuery += ", ";

	            }

	            updateQuery += "name = ?";

	            firstUpdate = false;

	        }



	        if (cnic != null && !cnic.isEmpty()) {

	            if (!firstUpdate) {

	                updateQuery += ", ";

	            }

	            updateQuery += "CNIC = ?";

	            firstUpdate = false;

	        }



	        if (email != null && !email.isEmpty()) {

	            if (!firstUpdate) {

	                updateQuery += ", ";

	            }

	            updateQuery += "email = ?";

	            firstUpdate = false;

	        }



	        if (phone != null && !phone.isEmpty()) {

	            if (!firstUpdate) {

	                updateQuery += ", ";

	            }

	            updateQuery += "phone = ?";

	            firstUpdate = false;

	        }



	        if (password != null && !password.isEmpty()) {

	            if (!firstUpdate) {

	                updateQuery += ", ";

	            }

	            updateQuery += "password = ?";

	            firstUpdate = false;

	        }



	        updateQuery += " WHERE CID = ?";



	        PreparedStatement updatePs = conn.prepareStatement(updateQuery);

	        int index = 1;



	        if (name != null && !name.isEmpty()) {

	            updatePs.setString(index++, name);

	        }

	        if (cnic != null && !cnic.isEmpty()) {

	            updatePs.setString(index++, cnic);

	        }

	        if (email != null && !email.isEmpty()) {

	            updatePs.setString(index++, email);

	        }

	        if (phone != null && !phone.isEmpty()) {

	            updatePs.setString(index++, phone);

	        }

	        if (password != null && !password.isEmpty()) {

	            updatePs.setString(index++, password);

	        }

	        updatePs.setInt(index, customerID);



	        int rowsUpdated = updatePs.executeUpdate();



	        return rowsUpdated > 0 ? 1 : 0;

	    } catch (SQLException e) {

	        System.out.println("Error updating customer: " + e.getMessage());

	        return 0;

	    } 

	}
	
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
