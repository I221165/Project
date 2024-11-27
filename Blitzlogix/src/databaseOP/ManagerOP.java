package databaseOP;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class ManagerOP {
	
	/////////////////////////////////////login verifier////////////////////////////////////
	
	
	public static boolean verifyManagerLogin(int mid, String password) {
	    Statement st = DatabaseConnection.getStatement();
	    Connection conn = DatabaseConnection.getConn();
	    if (statementChecker(st) == 0 || connChecker(conn) == 0) {
	        return false; // Indicate failure to connect
	    }

	    try {
	    	
	    
	    	
	        String selectQuery = "SELECT password FROM Manager WHERE MID = ?";
	        PreparedStatement ps = conn.prepareStatement(selectQuery);
	        ps.setInt(1, mid);
	        ResultSet rs = ps.executeQuery();

	        if (rs.next()) {
	            String storedPassword = rs.getString("password");
	            // Compare entered password with stored password (secure hashing recommended)
	            if (password.equals(storedPassword)) { // This is not secure, see note below
	                rs.close();
	                ps.close();
	                return true;
	            }
	        }
	        rs.close();
	        ps.close();
	    } catch (SQLException e) {
	        System.out.println("Error verifying manager login: " + e.getMessage());
	    }

	    return false;
	}
	
	
	///////////////////////////////////////////registration and removal ////////////////////////////
	
	
	
	public static int registerManager(String cnic, String name, String phone, String email, String password, int centerID) {
	    Statement st = DatabaseConnection.getStatement();
	    Connection conn = DatabaseConnection.getConn();
	    if (statementChecker(st) == 0 || connChecker(conn) == 0) {
	        return 0;
	    }

	    try {

	        UserOP.makeUser(cnic, name, phone, email);

	        // Insert manager into the database
	        String insertQuery = "INSERT INTO Manager (password, CNIC, center_id) VALUES (?, ?, ?)";
	        PreparedStatement insertPs = conn.prepareStatement(insertQuery);
	        insertPs.setString(1, password);
	        insertPs.setString(2, cnic);
	        insertPs.setInt(3, centerID);
	        insertPs.executeUpdate();

	        return 1;
	    } catch (SQLException e) {
	        System.out.println("Error saving manager data in database: " + e.getMessage());
	        return 0;
	    }
	}

	
	
	
	
	
	
	public static boolean removeManager(int managerID) {
	    Statement st = DatabaseConnection.getStatement();
	    Connection conn = DatabaseConnection.getConn();
	    if (statementChecker(st) == 0 || connChecker(conn) == 0) {
	        return false;
	    }

	    try {
	        String deleteQuery = "DELETE FROM Manager WHERE MID = ?";
	        PreparedStatement deletePs = conn.prepareStatement(deleteQuery);
	        deletePs.setInt(1, managerID);
	        deletePs.executeUpdate();

	        return true; 
	    } catch (SQLException e) {
	        System.out.println("Error removing manager: " + e.getMessage());
	        return false;
	    }
	}
	
	

	//////////////////////////displaying intercity and intracity drivers/////////////////////////////
	
	public static ResultSet getIntercityDriverIDsForManager(int mid) {
	    Statement st = DatabaseConnection.getStatement();
	    Connection conn = DatabaseConnection.getConn();
	    if (statementChecker(st) == 0 || connChecker(conn) == 0) {
	        return null;
	    }

	    try {
	        // Get the manager's center ID
	        int centerID = getCenterIDForManager(mid);
	        if (centerID == 0) {
	            return null; // Manager not found
	        }

	        String selectQuery = "SELECT d.DID " +
	                             "FROM Driver d " +
	                             "JOIN UserDetails u ON d.CNIC = u.CNIC " +
	                             "WHERE d.currentCenter = ? AND d.driver_type = 'InterCity' AND d.outForDelivery = false";
	        PreparedStatement ps = conn.prepareStatement(selectQuery);
	        ps.setInt(1, centerID);
	        ResultSet rs = ps.executeQuery();

	        return rs;
	    } catch (SQLException e) {
	        System.out.println("Error fetching intercity drivers for manager: " + e.getMessage());
	        return null;
	    }
	}
	
	
	public static ResultSet getIntercityDriversForManager(int mid) {
	    Statement st = DatabaseConnection.getStatement();
	    Connection conn = DatabaseConnection.getConn();
	    if (statementChecker(st) == 0 || connChecker(conn) == 0) {
	        return null;
	    }

	    try {
	        // Get the manager's center ID
	        int centerID = getCenterIDForManager(mid);
	        if (centerID == 0) {
	            return null; // Manager not found
	        }

	        String selectQuery = "SELECT d.DID, u.name AS driver_name, d.driver_type " +
	                             "FROM Driver d " +
	                             "JOIN UserDetails u ON d.CNIC = u.CNIC " +
	                             "WHERE d.currentCenter = ? AND d.driver_type = 'InterCity' AND d.outForDelivery = false";
	        PreparedStatement ps = conn.prepareStatement(selectQuery);
	        ps.setInt(1, centerID);
	        ResultSet rs = ps.executeQuery();

	        return rs;
	    } catch (SQLException e) {
	        System.out.println("Error fetching intercity drivers for manager: " + e.getMessage());
	        return null;
	    }
	}
	
	
	
	
	
	//////////////////////////////////now intracity/////////////////////////////////////
	
	
	public static ResultSet getIntracityDriverIDsForManager(int mid) {
	    Statement st = DatabaseConnection.getStatement();
	    Connection conn = DatabaseConnection.getConn();
	    if (statementChecker(st) == 0 || connChecker(conn) == 0) {
	        return null;
	    }

	    try {
	        // Get the manager's center ID
	        int centerID = getCenterIDForManager(mid);
	        if (centerID == 0) {
	            return null; // Manager not found
	        }

	        String selectQuery = "SELECT d.DID " +
	                             "FROM Driver d " +
	                             "JOIN UserDetails u ON d.CNIC = u.CNIC " +
	                             "WHERE d.currentCenter = ? AND d.driver_type = 'IntraCity' AND d.outForDelivery = false";
	        PreparedStatement ps = conn.prepareStatement(selectQuery);
	        ps.setInt(1, centerID);
	        ResultSet rs = ps.executeQuery();

	        return rs;
	    } catch (SQLException e) {
	        System.out.println("Error fetching intracity drivers for manager: " + e.getMessage());
	        return null;
	    }
	}
	
	public static ResultSet getIntracityDriversForManager(int mid) {
	    Statement st = DatabaseConnection.getStatement();
	    Connection conn = DatabaseConnection.getConn();
	    if (statementChecker(st) == 0 || connChecker(conn) == 0) {
	        return null;
	    }

	    try {
	        // Get the manager's center ID
	        int centerID = getCenterIDForManager(mid);
	        if (centerID == 0) {
	            return null; // Manager not found
	        }

	        String selectQuery = "SELECT d.DID, u.name AS driver_name, d.driver_type " +
	                             "FROM Driver d " +
	                             "JOIN UserDetails u ON d.CNIC = u.CNIC " +
	                             "WHERE d.currentCenter = ? AND d.driver_type = 'IntraCity' AND d.outForDelivery = false";
	        PreparedStatement ps = conn.prepareStatement(selectQuery);
	        ps.setInt(1, centerID);
	        ResultSet rs = ps.executeQuery();

	        return rs;
	    } catch (SQLException e) {
	        System.out.println("Error fetching intracity drivers for manager: " + e.getMessage());
	        return null;
	    }
	}
	
	
	                         //displaying packages to assign
	
	
	
	////////////////////////////////now intercity parcels to assign/////////////////////////////
	
	
	public static ResultSet getIntercityParcelsForManager(int mid){
	    Statement st = DatabaseConnection.getStatement();
	    Connection conn = DatabaseConnection.getConn();

	    if (statementChecker(st) == 0 || connChecker(conn) == 0) {
	        return null;
	    }

	    try {
	        int centerID = getCenterIDForManager(mid);
	        if (centerID == 0) {
	            return null; // Manager not found
	        }

	        String centerCity = CenterOP.getCenterCity(centerID);
	        if (centerCity == null) {
	            return null; // Error fetching center city
	        }

	        String selectQuery = "SELECT p.parcel_id, u1.name AS sender_name, a1.city AS source_city, " +
	                             "u2.name AS receiver_name, a2.city AS destination_city, p.status " +
	                             "FROM Parcel p " +
	                             "JOIN Customer c1 ON p.sender_id = c1.CID " +
	                             "JOIN UserDetails u1 ON c1.CNIC = u1.CNIC " +
	                             "JOIN Address a1 ON c1.address_id = a1.id " +
	                             "JOIN Customer c2 ON p.receiver_id = c2.CID " +
	                             "JOIN UserDetails u2 ON c2.CNIC = u2.CNIC " +
	                             "JOIN Address a2 ON c2.address_id = a2.id " +
	                             "WHERE p.status = 'DROPPED_AT_LOCALCENTER' " +
	                             "AND p.holder_id = ? " +
	                             "AND (SELECT city FROM Address WHERE id = (SELECT destination_id FROM Route WHERE route_id = p.route_id)) <> ?";

	        PreparedStatement ps = conn.prepareStatement(selectQuery);
	        ps.setInt(1, centerID);
	        ps.setString(2, centerCity);
	        ResultSet rs = ps.executeQuery();

	        return rs;
	    } catch (SQLException e) {
	        System.out.println("Error fetching parcel records for manager: " + e.getMessage());
	       
	    } 
	    
	    return null;
	}
	
	
	
	
	
	
	
	

	////////////////////////////////now intracity parcels to assign/////////////////////////////
	
	
	public static ResultSet getIntracityParcelsForManager(int mid){
		  Statement st = DatabaseConnection.getStatement();
		  Connection conn = DatabaseConnection.getConn();
		  if (statementChecker(st) == 0 || connChecker(conn) == 0) {
		    return null;
		  }

		  try {
			  
		    int centerID = getCenterIDForManager(mid);
		    if (centerID == 0) {
		      return null; 
		    }

		    
		    String centerCity = CenterOP.getCenterCity(centerID);
		    if (centerCity == null) {
		      return null;
		    }
		    
		    
		    ////////for within city deliveries
		    
		    String updateQuery = "UPDATE Parcel SET status = 'AT_DESTINATION_CENTER' " +
                    "WHERE status = 'DROPPED_AT_LOCALCENTER' " +
                    "AND (SELECT city FROM Address WHERE id = (SELECT destination_id FROM Route WHERE route_id = Parcel.route_id)) = ?";
            PreparedStatement updatePs = conn.prepareStatement(updateQuery);
            updatePs.setString(1, centerCity);
            int updatedRows =updatePs.executeUpdate();
            
            ArrayList<Integer> updatedParcelIDs = new ArrayList<>();
            if (updatedRows > 0) {
                ResultSet updatedParcels = updatePs.getGeneratedKeys();
                while (updatedParcels.next()) {
                    updatedParcelIDs.add(updatedParcels.getInt(1));
                }
            }
            
            

		    ////////////////////////////////////////////////////

		    String selectQuery = "SELECT p.parcel_id, u1.name AS sender_name, a1.city AS source_city, " +
		                          "u2.name AS receiver_name, a2.city AS destination_city, p.status " +
		                          "FROM Parcel p " +
		                          "JOIN Customer c1 ON p.sender_id = c1.CID " +
		                          "JOIN UserDetails u1 ON c1.CNIC = u1.CNIC " +
		                          "JOIN Address a1 ON c1.address_id = a1.id " +
		                          "JOIN Customer c2 ON p.receiver_id = c2.CID " +
		                          "JOIN UserDetails u2 ON c2.CNIC = u2.CNIC " +
		                          "JOIN Address a2 ON c2.address_id = a2.id " +
		                          "WHERE (p.status = 'REQUEST_INITIATED' OR p.status = 'AT_DESTINATION_CENTER') "+
		                          "AND (SELECT city FROM Address WHERE id = (SELECT destination_address_id FROM Route WHERE route_id = Parcel.route_id)) = ?";
		    PreparedStatement ps = conn.prepareStatement(selectQuery);
		    ps.setString(1, centerCity);
		    ResultSet rs = ps.executeQuery();

		    return rs;
		  } catch (SQLException e) {
		    System.out.println("Error fetching parcel records for manager: " + e.getMessage());
		    return null;
		  }
		}
	
	///////////////////////////////////////////now for the assign button/////////////////////////
	
	//expecting a driverID and a parcel ID
	
	
	
	//center sa sirf drivers ko jaa sakta ha, 
	
	//they manage on basis of status and source and estination citiies
	public static int assignParcel(int parcelID, int driverID){
	    Statement st = DatabaseConnection.getStatement();
	    Connection conn = DatabaseConnection.getConn();

	    if (statementChecker(st) == 0 || connChecker(conn) == 0) {
	        return 0; // Indicate failure
	    }

	        ParcelOP.updateParcelHolderID(parcelID, driverID);
	        ParcelOP.moveStatusForward(parcelID);

	        return 1; // Assignment successful
	   
	}

    
	
	
	////////////////////////////YOU WONT NEED ANYTHING BELOW THAT//////////////////////////////////
	
	
	
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
	
	public static int managerExists(int mid) {
	    Statement st = DatabaseConnection.getStatement();
	    Connection conn = DatabaseConnection.getConn();
	    if (statementChecker(st) == 0 || connChecker(conn) == 0) {
	        return 0; 
	    }

	    try {
	        String selectQuery = "SELECT MID FROM Manager WHERE MID = ?";
	        PreparedStatement ps = conn.prepareStatement(selectQuery);
	        ps.setInt(1, mid);
	        ResultSet rs = ps.executeQuery();

	        if (rs.next()) {
	            int managerID = rs.getInt(1);
	            rs.close();
	            ps.close();
	            return managerID;
	        } else {
	            rs.close();
	            ps.close();
	            return 0; 
	        }
	    } catch (SQLException e) {
	        System.out.println("Error fetching manager ID: " + e.getMessage());
	        return -1; 
	    }
	}
	
	
	//return id or 0
	public static int managerExists(String cnic) {
	    Statement st = DatabaseConnection.getStatement();
	    Connection conn = DatabaseConnection.getConn();
	    if (statementChecker(st) == 0 || connChecker(conn) == 0) {
	        return 0; // Indicate failure
	    }

	    try {
	        String selectQuery = "SELECT MID FROM Manager WHERE CNIC = ?";
	        PreparedStatement ps = conn.prepareStatement(selectQuery);
	        ps.setString(1, cnic);
	        ResultSet rs = ps.executeQuery();

	        if (rs.next()) {
	            int managerID = rs.getInt(1);
	            rs.close();
	            ps.close();
	            return managerID;
	        } else {
	            rs.close();
	            ps.close();
	            return 0; 
	        }
	    } catch (SQLException e) {
	        System.out.println("Error fetching manager ID: " + e.getMessage());
	        return -1; 
	    }
	}
	
	
	
	//////////////////////////////////////////////CRUDS/////////////////

	
	////////////////////////////////////////get centerID
	
	public static int getCenterIDForManager(int mid) {
	    Statement st = DatabaseConnection.getStatement();
	    Connection conn = DatabaseConnection.getConn();
	    if (statementChecker(st) == 0 || connChecker(conn) == 0) {
	        return -1; // Indicate failure or error
	    }

	    try {
	        String selectQuery = "SELECT center_id FROM Manager WHERE MID = ?";
	        PreparedStatement ps = conn.prepareStatement(selectQuery);
	        ps.setInt(1, mid);
	        ResultSet rs = ps.executeQuery();

	        if (rs.next()) {
	            int centerID = rs.getInt("center_id");
	            rs.close();
	            ps.close();
	            return centerID;
	        } else {
	            rs.close();
	            ps.close();
	            return 0; // No manager found
	        }
	    } catch (SQLException e) {
	        System.out.println("Error fetching manager's center ID: " + e.getMessage());
	        return -1; // Indicate failure or error
	    }
	}
	
	
	
	
	
	
}
