package databaseOP;
import model.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class CustomerOP {
	 
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
	
	public static int searchCustomer(String cnic) {
		Statement st = DatabaseConnection.getStatement();
	    Connection conn = DatabaseConnection.getConn();
	    if (statementChecker(st) == 0 || connChecker(conn) == 0) {
	        return 0;
	    }

	    try {
	        String checkQuery = "SELECT CID FROM Customer WHERE CNIC = ?";
	        PreparedStatement ps = conn.prepareStatement(checkQuery);
	        ps.setString(1, cnic);
	        ResultSet rs = ps.executeQuery();

	        if (rs.next()) {
	            int cid = rs.getInt(1); 
	            rs.close();
	            ps.close();
	            return cid;
	        } else {
	            rs.close();
	            ps.close();
	            return 0;  ///means no such customer
	        }
	    } catch (SQLException e) {
	        System.out.println("Error searching customer: " + e.getMessage());
	        return 0;
	    }
	}
	
	public static int searchCustomer(int customerID) {
		Statement st = DatabaseConnection.getStatement();
	    Connection conn = DatabaseConnection.getConn();
	    if (statementChecker(st) == 0 || connChecker(conn) == 0) {
	        return 0;
	    }

	    try {
	        String checkQuery = "SELECT CID FROM Customer WHERE CID = ?";
	        PreparedStatement ps = conn.prepareStatement(checkQuery);
	        ps.setInt(1, customerID); // Set the actual customer ID
	        ResultSet rs = ps.executeQuery();

	        if (rs.next()) {
	            int cid = rs.getInt(1); 
	            rs.close();
	            ps.close();
	            return cid; //will return the same id as provided
	        } else {
	            rs.close();
	            ps.close();
	            return 0; 
	        }
	    } catch (SQLException e) {
	        System.out.println("Error searching customer: " + e.getMessage());
	        return 0; 
	    }
	}
	
	
	
	//////////////////////////////////////CRUD stuff
	
	
	public static int registerCustomer(String cnic, String name, String phone, String email, String password) {
	    Statement st = DatabaseConnection.getStatement();
	    Connection conn = DatabaseConnection.getConn();
	    if (statementChecker(st) == 0 || connChecker(conn) == 0) {
	        return 0;
	    }

	    try {
	        
	    	if(searchCustomer(cnic) != 0) { //some customer with same cnic exists
	    		return 0;
	    	}
	    	
	    	UserOP.makeUser(cnic, name, phone, email);//we dont care if it already exists or not
	    	                                          //we just happy its there
	    	
	    	
	        String insertQuery = "INSERT INTO Customer (password,cnic) VALUES (?, ?)";
	        PreparedStatement insertPs = conn.prepareStatement(insertQuery);
	        insertPs.setString(1, password);
	        insertPs.setString(2, cnic);
	        insertPs.executeUpdate();

	        return 1; 
	    } catch (SQLException e) {
	        System.out.println("Error saving data in database: " + e.getMessage());
	        return 0; 
	    }
	}
	
	
	
	
	
	public static int incrementCustomerParcelSent(int customerID) {
		Statement st = DatabaseConnection.getStatement();
	    Connection conn = DatabaseConnection.getConn();
	    if (statementChecker(st) == 0 || connChecker(conn) == 0) {
	        return 0;
	    }

	    try {
	    	if(searchCustomer(customerID) == 0) {
	    		return 0; //no such customer exists
	    	}
	        String updateQuery = "UPDATE Customer SET total_parcels = total_parcels + 1 WHERE CID = ?";
	        PreparedStatement ps = conn.prepareStatement(updateQuery);
	        ps.setInt(1, customerID);
	        ps.executeUpdate();
	        return 1;
	    } catch (SQLException e) {
	        System.out.println("Error updating customer parcels: " + e.getMessage());
	        return 0;
	    }
	}
	
	
	
	
	
	public static int addMoneySpent(int customerID, double amount) {
		Statement st = DatabaseConnection.getStatement();
	    Connection conn = DatabaseConnection.getConn();
	    if (statementChecker(st) == 0 || connChecker(conn) == 0) {
	        return 0;
	    }
	    try {
	        if (searchCustomer(customerID) == 0) {
	            return 0; // No such customer exists
	        }

	        String updateQuery = "UPDATE Customer SET money_spent = money_spent + ? WHERE CID = ?";
	        PreparedStatement ps = conn.prepareStatement(updateQuery);
	        ps.setDouble(1, amount);
	        ps.setInt(2, customerID);
	        ps.executeUpdate();

	        return 1; 
	    } catch (SQLException e) {
	        System.out.println("Error adding money spent: " + e.getMessage());
	        return 0; 
	    }
	}
	
	
	
}
