package databaseOP;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class CustomerOP {
	
	
	////////////////////////////////////login verifier
	
	public static boolean verifyLogin(int cid, String password) {
	    Statement st = DatabaseConnection.getStatement();
	    Connection conn = DatabaseConnection.getConn();
	    if (statementChecker(st) == 0 || connChecker(conn) == 0) {
	        return false;
	    }

	    try {
	        String loginQuery = "SELECT * FROM Customer WHERE CID = ? AND password = ?";
	        PreparedStatement ps = conn.prepareStatement(loginQuery);
	        ps.setInt(1, cid);
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
	        System.out.println("Error verifying customer login: " + e.getMessage());
	        return false;
	    }
	}
	
	
	
	/////////////////////////////////////registration 
	
	public static boolean registerCustomer(String cnic, String name, String phone, String email, String password) {
	    Statement st = DatabaseConnection.getStatement();
	    Connection conn = DatabaseConnection.getConn();
	    if (statementChecker(st) == 0 || connChecker(conn) == 0) {
	        return false;
	    }

	    try {
	        
	    	if(searchCustomer(cnic) != 0) { //some customer with same cnic exists
	    		return false;
	    	}
	    	
	    	UserOP.makeUser(cnic, name, phone, email);//we dont care if it already exists or not
	    	                                          //we just happy its there
	    	
	    	
	        String insertQuery = "INSERT INTO Customer (password,cnic) VALUES (?, ?)";
	        PreparedStatement insertPs = conn.prepareStatement(insertQuery);
	        insertPs.setString(1, password);
	        insertPs.setString(2, cnic);
	        insertPs.executeUpdate();

	        return true; 
	    } catch (SQLException e) {
	        System.out.println("Error saving data in database: " + e.getMessage());
	        return false; 
	    }
	}
	
	
	
	
	
	//////////////////////////PARCEL RECEIVing //////////////////////////////////
	
	
	////////////////////////////now one to show parcels to receive
	
	///////////////parcels to receive
	
	
	public static ResultSet getCustomerParcelsToReceive(int cid) {
	    Statement st = DatabaseConnection.getStatement();
	    Connection conn = DatabaseConnection.getConn();
	    if (statementChecker(st) == 0 || connChecker(conn) == 0) {
	        return null;
	    }

	    try {
	        String selectQuery = "SELECT p.parcel_id, u.name AS sender_name, s.city AS source_city, d.city AS destination_city, p.status " +
	                             "FROM Parcel p " +
	                             "JOIN Route r ON p.route_id = r.route_id " +
	                             "JOIN Address s ON r.source_id = s.id " +
	                             "JOIN Address d ON r.destination_id = d.id " +
	                             "JOIN Customer c ON p.receiver_id = c.CID " +
	                             "JOIN Customer s_cust ON p.sender_id = s_cust.CID " +
	                             "JOIN UserDetails u ON s_cust.CNIC = u.CNIC " +
	                             "WHERE c.CID = ? AND " +
	                             "(p.status = 'DELIVERED')";
	        PreparedStatement ps = conn.prepareStatement(selectQuery);
	        ps.setInt(1, cid);
	        ResultSet rs = ps.executeQuery();
	        

	        return rs;
	    } catch (SQLException e) {
	        System.out.println("Error fetching customer's parcels to receive: " + e.getMessage());
	        return null;
	    }
	}
	
	
	
	
	
	//change the holderid here and move the status forward too and toggle the boolean too
	//incrmnet parcels delivered too
	//recive button
	
	
	
	
	public static boolean receiveParcelButton(int cid, int parcelId) {
	    Statement st = DatabaseConnection.getStatement();
	    Connection conn = DatabaseConnection.getConn();

	    if (statementChecker(st) == 0 || connChecker(conn) == 0) {
	        return false; 
	    }

	    try {

	        String checkQuery = "SELECT * FROM Parcel p " +
	                            "JOIN Customer c ON p.receiver_id = c.CID " +
	                            "WHERE p.parcel_id = ? AND c.CID = ? AND  p.status IN ('DELIVERED')";
	        PreparedStatement checkPs = conn.prepareStatement(checkQuery);
	        checkPs.setInt(1, parcelId);
	        checkPs.setInt(2, cid);
	        ResultSet rs = checkPs.executeQuery();

	        if (!rs.next()) {
	            System.out.println("Parcel not found or does not belong to the customer.");
	            return false; 
	        }

            //can be a fascade
	        ParcelOP.moveStatusForward(parcelId); //now finished
	        DriverOP.toggleDriverOutForDelivery(ParcelOP.getHolderIDFromParcel(parcelId));//it was of driver till now
	        DriverOP.incrementDriverParcelsDelivered(ParcelOP.getHolderIDFromParcel(parcelId));
	        ParcelOP.updateParcelHolderID(parcelId, cid);//now customer ID


	        return true; 
	    } catch (SQLException e) {
	        System.out.println("Error receiving parcel: " + e.getMessage());
	        return false;
	    } 
	}

	

	
	
	 /////////////////////////////////YOU WONT NEED ANYTHING BELOW THAT////////////////////////////
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
	
	
   ///////////////////////////////searchers(operator overloading)/////////
	
	public static String getCustomerName(int customerID) {
		Statement st = DatabaseConnection.getStatement();
	    Connection conn = DatabaseConnection.getConn();
	    if (statementChecker(st) == 0 || connChecker(conn) == 0) {
	        return "";
	    }
	    if (statementChecker(st) == 0) {
	        return "";
	    }

	    try {
	        String selectQuery = "SELECT name FROM UserDetails JOIN Customer ON UserDetails.CNIC = Customer.CNIC WHERE Customer.CID = ?";
	        PreparedStatement ps = conn.prepareStatement(selectQuery);
	        ps.setInt(1, customerID);
	        ResultSet rs = ps.executeQuery();

	        if (rs.next()) {
	            String name = rs.getString(1);
	            rs.close();
	            ps.close();
	            return name;
	        } else {
	            rs.close();
	            ps.close();
	            return ""; 
	        }
	    } catch (SQLException e) {
	        System.out.println("Error fetching customer name: " + e.getMessage());
	        return "";
	    }
	}
	
	
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
	
	private  static int searchCustomer(int customerID) {
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
		
	
	//////////////////////////////////////////////////////////////////////////////////////////
	
}
