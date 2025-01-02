package databaseOP;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class PaymentOP {
	
	//READ THIS FOR PAYMENT, YOU DONT NEED TO USE ANY FUNCTION
	
         //here when you get all details, make a parcel object from model class
	     //constrcutor requires only id
	
	     //you make a payement object and give it the strategy, string name, confirm spellings
	      //one of : CardPayment,CashPayment,OnlineTransfer, better use enum class
	
	    
	   //then if its cash, do p.setnem and add name to the p object 
	
	   //if its card, do p.addbank and p.cardnumber
	
	  //if its online, do p.addbank and p.addtrransactionid 
	
	  //in any case, at end just do p.process---see the name from the model class
	
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

	
	
	/////////////////////////////////basic at time of parcel creation/////////////
	
	
	public static int insertPayment(int senderID, int parcelID) {
	    Statement st = DatabaseConnection.getStatement();
	    Connection conn = DatabaseConnection.getConn();
	    if (statementChecker(st) == 0 || connChecker(conn) == 0) {
	        return 0;
	    }

	    try {

	        String senderName = CustomerOP.getCustomerName(senderID);

	        String insertQuery = "INSERT INTO Payments (sender_id, parcel_id, Payer_name) VALUES (?, ?, ?)";
	        PreparedStatement insertPs = conn.prepareStatement(insertQuery);
	        insertPs.setInt(1, senderID);
	        insertPs.setInt(2, parcelID);
	        insertPs.setString(3, senderName);
	        insertPs.executeUpdate();

	        return 1; 
	    } catch (SQLException e) {
	        System.out.println("Error inserting payment: " + e.getMessage());
	        return 0;
	    }
	}
	
	
	
	
	

	//updates money spent_too 
	public static int markPaymentAsCompleted(int parcelID) {
	    Statement st = DatabaseConnection.getStatement();
	    Connection conn = DatabaseConnection.getConn();
	    if (statementChecker(st) == 0 || connChecker(conn) == 0) {
	        return 0;
	    }

	    try {
	        // Update the payment status
	        String updatePaymentQuery = "UPDATE Payments SET done = true WHERE parcel_id = ?";
	        PreparedStatement updatePaymentPs = conn.prepareStatement(updatePaymentQuery);
	        updatePaymentPs.setInt(1, parcelID);
	        updatePaymentPs.executeUpdate();

	        // Retrieve the sender ID from the Payments table
	        String selectSenderQuery = "SELECT sender_id FROM Payments WHERE parcel_id = ?";
	        PreparedStatement selectSenderPs = conn.prepareStatement(selectSenderQuery);
	        selectSenderPs.setInt(1, parcelID);
	        ResultSet rs = selectSenderPs.executeQuery();

	        int senderID = 0;
	        if (rs.next()) {
	            senderID = rs.getInt("sender_id");
	        } else {

	            System.out.println("Parcel ID not found in Payments table.");
	            return 0;
	        }


	        String updateCustomerQuery = "UPDATE Customer SET money_spent = money_spent + (SELECT amount FROM Payments WHERE parcel_id = ?) WHERE CID = ?";
	        PreparedStatement updateCustomerPs = conn.prepareStatement(updateCustomerQuery);
	        updateCustomerPs.setInt(1, parcelID);
	        updateCustomerPs.setInt(2, senderID);
	        updateCustomerPs.executeUpdate();

	        return 1; 

	    } catch (SQLException e) {
	        System.out.println("Error marking payment as completed: " + e.getMessage());
	        return 0; // Error marking payment as completed
	    } finally {
	        // Close resources to prevent resource leaks
	        try {
	            if (st != null) {
	                st.close();
	            }
	            if (conn != null) {
	                conn.close();
	            }
	        } catch (SQLException e) {
	            System.out.println("Error closing database resources: " + e.getMessage());
	        }
	    }
	}
	
	
	
	
	

	public static ResultSet getUnpaidPaymentsForCustomer(int customerID){

	    Statement st = DatabaseConnection.getStatement();

	    Connection conn = DatabaseConnection.getConn();



	    if (statementChecker(st) == 0 || connChecker(conn) == 0) {

	        return null;

	    }



	    try {

	        String selectQuery = "SELECT parcel_id FROM Payments WHERE sender_id = ? AND done = FALSE";

	        PreparedStatement ps = conn.prepareStatement(selectQuery);

	        ps.setInt(1, customerID);

	        ResultSet rs = ps.executeQuery();



	        return rs;

	    } catch (SQLException e) {

	        System.out.println("Error fetching unpaid payments for customer: " + e.getMessage());

	        return null;

	    }

	}
	
	
	
	
	
	
	
	
	///////////////////////////////////////////////////////////////////////////////////////
	public static int insertCashPayment(int parcelID) {
	    Statement st = DatabaseConnection.getStatement();
	    Connection conn = DatabaseConnection.getConn();
	    if (statementChecker(st) == 0 || connChecker(conn) == 0) {
	        return 0;
	    }

	    try {
	    	int senderID = ParcelOP.getSenderIDFromParcelID(parcelID);
	        // Get the sender's name using the senderID
	        String senderName = CustomerOP.getCustomerName(senderID);

	        String insertQuery = "INSERT INTO CashPayment (parcel_id, payer_name) VALUES (?, ?)";
	        PreparedStatement insertPs = conn.prepareStatement(insertQuery);
	        insertPs.setInt(1, parcelID);
	        insertPs.setString(2, senderName);
	        insertPs.executeUpdate();

	        markPaymentAsCompleted(parcelID); //marking in original table
	        
	        return 1; // Cash payment inserted successfully
	    } catch (SQLException e) {
	        System.out.println("Error inserting cash payment: " + e.getMessage());
	        return 0; // Error inserting cash payment
	    }
	}
	
	
	
	
	
	public static int insertOnlineTransfer(int parcelID, String bankName, String transactionID) {
	    Statement st = DatabaseConnection.getStatement();
	    Connection conn = DatabaseConnection.getConn();
	    if (statementChecker(st) == 0 || connChecker(conn) == 0) {
	        return 0;
	    }

	    try {
	        String insertQuery = "INSERT INTO OnlineTransfer (parcel_id, bank_name, transaction_id) VALUES (?, ?, ?)";
	        PreparedStatement insertPs = conn.prepareStatement(insertQuery);
	        insertPs.setInt(1, parcelID);
	        insertPs.setString(2, bankName);
	        insertPs.setString(3, transactionID);
	        insertPs.executeUpdate();

	        markPaymentAsCompleted(parcelID); //marking in original table
	        return 1; // Online transfer inserted successfully
	    } catch (SQLException e) {
	        System.out.println("Error inserting online transfer: " + e.getMessage());
	        return 0; // Error inserting online transfer
	    }
	}
	
	
	
	
	
	public static int insertCardPayment(int parcelID, String bankName, String cardNumber) {
	    Statement st = DatabaseConnection.getStatement();
	    Connection conn = DatabaseConnection.getConn();
	    if (statementChecker(st) == 0 || connChecker(conn) == 0) {
	        return 0;
	    }

	    try {
	        String insertQuery = "INSERT INTO CardPayment (parcel_id, bank_name, card_number) VALUES (?, ?, ?)";
	        PreparedStatement insertPs = conn.prepareStatement(insertQuery);
	        insertPs.setInt(1, parcelID);
	        insertPs.setString(2, bankName);
	        insertPs.setString(3, cardNumber);
	        insertPs.executeUpdate();

	        markPaymentAsCompleted(parcelID); //marking in original table
	        return 1; // Card payment inserted successfully
	    } catch (SQLException e) {
	        System.out.println("Error inserting card payment: " + e.getMessage());
	        return 0; // Error inserting card payment
	    }
	}
	
	
	
	
	
	
	/////////////////////record of payments for customer to pay/////////////////////////////////////////
	
	
	
	public static ResultSet getPaymentsBySenderID(int customerID) {
	    Statement st = DatabaseConnection.getStatement();
	    Connection conn = DatabaseConnection.getConn();
	    if (statementChecker(st) == 0 || connChecker(conn) == 0) {
	        return null;
	    }

	    try {
	        String query = "SELECT * FROM Payments WHERE sender_id = ? AND done = false";
	        PreparedStatement ps = conn.prepareStatement(query);
	        ps.setInt(1, customerID);
	        return ps.executeQuery();
	    } catch (SQLException e) {
	        System.out.println("Error retrieving payments: " + e.getMessage());
	        return null;
	    }
	}
	
	
	
	//to get parcel ids only
	public static ResultSet getPaymentIDsBySenderID(int customerID) {
	    Statement st = DatabaseConnection.getStatement();
	    Connection conn = DatabaseConnection.getConn();
	    if (statementChecker(st) == 0 || connChecker(conn) == 0) {
	        return null;
	    }

	    try {
	        String query = "SELECT parcel_id FROM Payments WHERE sender_id = ? AND done = false";
	        PreparedStatement ps = conn.prepareStatement(query);
	        ps.setInt(1, customerID);
	        return ps.executeQuery();
	    } catch (SQLException e) {
	        System.out.println("Error retrieving payments: " + e.getMessage());
	        return null;
	    }
	}
	
}
