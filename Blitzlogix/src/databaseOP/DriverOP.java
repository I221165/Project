package databaseOP;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import model.DriverType;

public class DriverOP {
	
	
	///////////////////////////////login verifier
	
	public static boolean verifyDriverLogin(int driverID, String password) {
	    Statement st = DatabaseConnection.getStatement();
	    Connection conn = DatabaseConnection.getConn();
	    if (statementChecker(st) == 0 || connChecker(conn) == 0) {
	        return false;
	    }

	    try {
	        String loginQuery = "SELECT * FROM Driver WHERE DID = ? AND password = ?";
	        PreparedStatement ps = conn.prepareStatement(loginQuery);
	        ps.setInt(1, driverID);
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
	        System.out.println("Error verifying driver login: " + e.getMessage());
	        return false;
	    }
	}

	
	
	
	///////////////////////////////////////registration//////////////////////////
	
	
	public static boolean registerDriver(String cnic, String name, String phone, String email, String password, String driverType, int currentCenter) {
	    Statement st = DatabaseConnection.getStatement();
	    Connection conn = DatabaseConnection.getConn();
	    if (statementChecker(st) == 0 || connChecker(conn) == 0) {
	        return false;
	    }

	    try {
	        // Check if the user (with the same CNIC) already exists
	        if (searchDriver(cnic) != 0) {
	            return false;
	        }

	        UserOP.makeUser(cnic, name, phone, email);
	        
	        if(driverType.equalsIgnoreCase(DriverType.IntraCity.name())) {
	        	driverType = DriverType.IntraCity.name();
	        }else {
	        	driverType = DriverType.InterCity.name();
	        }

	        String insertQuery = "INSERT INTO Driver (password, CNIC, driver_type, currentCenter) VALUES (?, ?, ?, ?)";
	        PreparedStatement insertPs = conn.prepareStatement(insertQuery);
	        insertPs.setString(1, password);
	        insertPs.setString(2, cnic);
	        insertPs.setString(3, driverType);
	        insertPs.setInt(4, currentCenter);
	        insertPs.executeUpdate();

	        return true;
	    } catch (SQLException e) {
	        System.out.println("Error saving driver data in database: " + e.getMessage());
	        return false;
	    }
	}
	
    ////////////////////////////////pickup and dropoff functions///////////////////////////////////
	
	public static boolean removeDriver(int driverID) {
	    Statement st = DatabaseConnection.getStatement();
	    Connection conn = DatabaseConnection.getConn();
	    if (statementChecker(st) == 0 || connChecker(conn) == 0) {
	        return false; 
	    }
	    
	    if(isDriverOutForDelivery(driverID) == true) { // out of town maybe
			  return false;
		  }

	    try {
	        // Check if the driver is currently holding any parcels
	        String checkParcelQuery = "SELECT COUNT(*) FROM Parcel WHERE holder = 'DRIVER' AND holder_id = ?";
	        PreparedStatement checkParcelPs = conn.prepareStatement(checkParcelQuery);
	        checkParcelPs.setInt(1, driverID);
	        ResultSet rs = checkParcelPs.executeQuery();

	        if (rs.next()) {
	            int count = rs.getInt(1);
	            if (count > 0) {
	                return false; 
	            }
	        }

	        // If no parcels are held by the driver, proceed with deletion
	        String deleteQuery = "DELETE FROM Driver WHERE DID = ?";
	        PreparedStatement deletePs = conn.prepareStatement(deleteQuery);
	        deletePs.setInt(1, driverID);
	        deletePs.executeUpdate();

	        return true; 
	    } catch (SQLException e) {
	        System.out.println("Error removing driver: " + e.getMessage());
	        return false;
	    }
	}
	
	
	
	
	
	///////////////////////////////////now driver related deliveries//////////////////////////
	
	//////////////////////to display in pickup//////////////////////////////////////////
	
	
	public static ResultSet getAssignedParcelIDsForDriver(int driverID) {
	    Statement st = DatabaseConnection.getStatement();
	    Connection conn = DatabaseConnection.getConn();
	    if (statementChecker(st) == 0 || connChecker(conn) == 0) {
	        return null;
	    }
	    
	    if(isDriverOutForDelivery(driverID) == true) { // out of town maybe
			  return null;
		  }

	    try {
	        String selectQuery = "SELECT p.parcel_id " +
	                             "FROM Parcel p " +
	                             "JOIN Route r ON p.route_id = r.route_id " +
	                             "JOIN Address s ON r.source_id = s.id " +
	                             "JOIN Address d ON r.destination_id = d.id " +
	                             "JOIN Driver dr ON p.holder_id = dr.DID " +
	                             "WHERE dr.DID = ? AND " +
	                             "(p.status = 'ASSIGNED_TO_DRIVER' OR " +
	                             "p.status = 'ASSIGNED_TRANSIT' OR " +
	                             "p.status = 'ASSIGNED_DELIVERY')";
	        PreparedStatement ps = conn.prepareStatement(selectQuery);
	        ps.setInt(1, driverID);
	        ResultSet rs = ps.executeQuery();

	        return rs;
	    } catch (SQLException e) {
	        System.out.println("Error fetching assigned parcels for driver: " + e.getMessage());
	        return null;
	    }
	}
	
	public static ResultSet getAssignedParcelsForDriver(int driverID) {
	    Statement st = DatabaseConnection.getStatement();
	    Connection conn = DatabaseConnection.getConn();
	    if (statementChecker(st) == 0 || connChecker(conn) == 0) {
	        return null;
	    }

	    if (isDriverOutForDelivery(driverID) == true) { // out of town maybe
	        return null;
	    }

	    try {
	        String selectQuery = "SELECT p.parcel_id, p.status " +
	                             "FROM Parcel p " +
	                             "JOIN Driver dr ON p.holder_id = dr.DID " +
	                             "WHERE dr.DID = ? AND " +
	                             "(p.status = 'ASSIGNED_TO_DRIVER' OR " +
	                             "p.status = 'ASSIGNED_TRANSIT' OR " +
	                             "p.status = 'ASSIGNED_DELIVERY')";

	        PreparedStatement ps = conn.prepareStatement(selectQuery);
	        ps.setInt(1, driverID);
	        ResultSet rs = ps.executeQuery();

	        return rs;
	    } catch (SQLException e) {
	        System.out.println("Error fetching assigned parcels for driver: " + e.getMessage());
	        return null;
	    }
	}
	
	
	
	
	/////////////////////to display parcels to drop off
	
	
	public static ResultSet getParcelsToDropOffForDriver(int driverID) {
	    Statement st = DatabaseConnection.getStatement();
	    Connection conn = DatabaseConnection.getConn();
	    if (statementChecker(st) == 0 || connChecker(conn) == 0) {
	        return null;
	    }

	    try {
	        String selectQuery = "SELECT p.parcel_id, s.city AS source_city, d.city AS destination_city, p.status " +
	                             "FROM Parcel p " +
	                             "JOIN Route r ON p.route_id = r.route_id " +
	                             "JOIN Address s ON r.source_id = s.id " +
	                             "JOIN Address d ON r.destination_id = d.id " +
	                             "JOIN Driver dr ON p.holder_id = dr.DID " +
	                             "WHERE dr.DID = ? AND " +
	                             "(p.status = 'PICKED_FROM_SENDER' OR " +
	                             "p.status = 'IN_TRANSIT' OR " +
	                             "p.status = 'OUT_FOR_DELIVERY')";
	        PreparedStatement ps = conn.prepareStatement(selectQuery);
	        ps.setInt(1, driverID);
	        ResultSet rs = ps.executeQuery();

	        return rs;
	    } catch (SQLException e) {
	        System.out.println("Error fetching parcels to drop off for driver: " + e.getMessage());
	        return null;
	    }
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	////////////////////////////////////////functions for pickup and dropoff buttons/////////////////
	
	
	///pickup(same for intercity and intracity parcels)
	
	
	
	public static int pickupParcel(int driverID, int parcelID) {
		  Statement st = DatabaseConnection.getStatement();
		  Connection conn = DatabaseConnection.getConn();
		  if (statementChecker(st) == 0 || connChecker(conn) == 0) {
		    return 0;
		  }
		  
		  if(isDriverOutForDelivery(driverID) == true) { // out of town maybe
			  return 0;
		  }

		  try {

		    String checkQuery = "SELECT * FROM Parcel WHERE parcel_id = ? AND holder_id = ?";
		    PreparedStatement checkPs = conn.prepareStatement(checkQuery);
		    checkPs.setInt(1, parcelID);
		    checkPs.setInt(2, driverID);
		    ResultSet rs = checkPs.executeQuery();

		    if (!rs.next()) {
		      System.out.println("Driver is not assigned to pick up this parcel");
		      return 0;
		    }
             
		    
		    String cityName = "";   
		    if(ParcelOP.getParcelStatus(parcelID).equalsIgnoreCase("ASSIGNED_TO_DRIVER") || ParcelOP.getParcelStatus(parcelID).equalsIgnoreCase("ASSIGNED_TRANSIT")) {
		    	cityName = ParcelOP.getSourceCityFromParcelID(parcelID);
		    }else {
		    	cityName = ParcelOP.getDestinationCityFromParcelID(parcelID);
		    }
		    
		    int centerID = CenterOP.getCenterIDByCity(cityName);
		    
		    changeDriverCurrentCenter(driverID, centerID);
		    //now the currentCenter to where it last picked or dropped basically
		
		    
		    
		    ParcelOP.moveStatusForward(parcelID);
	    
		    
		    
		    return 1;
		  } catch (SQLException e) {
		    System.out.println("Error picking up parcel: " + e.getMessage());
		    return 0;
		  }
		}
	
	
	/////dropoff
	
	
	
	
	public static int dropOffParcel(int driverID, int parcelID) {
		  Statement st = DatabaseConnection.getStatement();
		  Connection conn = DatabaseConnection.getConn();
		  if (statementChecker(st) == 0 || connChecker(conn) == 0) {
		    return 0;
		  }
		  
		  if(isDriverOutForDelivery(driverID) == true) { // out of town maybe
			  return 0;
		  }

		  try {
		    String checkQuery = "SELECT * FROM Parcel WHERE parcel_id = ? AND holder_id = ?";
		    PreparedStatement checkPs = conn.prepareStatement(checkQuery);
		    checkPs.setInt(1, parcelID);
		    checkPs.setInt(2, driverID);
		    ResultSet rs = checkPs.executeQuery();

		    if (!rs.next()) {
		      System.out.println("Driver is not assigned to deliver this parcel");
		      return 0;
		    }

		    String cityName;
		    String currentStatus = ParcelOP.getParcelStatus(parcelID);
		    if (currentStatus.equalsIgnoreCase("IN_TRANSIT") || currentStatus.equalsIgnoreCase("OUT_FOR_DELIVERY")) {
		      cityName = ParcelOP.getDestinationCityFromParcelID(parcelID);
		    } else {
		      cityName = ParcelOP.getSourceCityFromParcelID(parcelID);
		    }

		    int centerID = CenterOP.getCenterIDByCity(cityName);
		    
		    
		    changeDriverCurrentCenter(driverID, centerID);
		    if (currentStatus.equalsIgnoreCase("OUT_FOR_DELIVERY")) {
		      toggleDriverOutForDelivery(driverID);
		    }
		    else {
		    	ParcelOP.updateParcelHolderID(parcelID, centerID);
		    }
		    ParcelOP.moveStatusForward(parcelID);
		    
		    //still it will have the holderid incase of delivered/out for delivery
		    //that will be changed in receive

		    return 1;
		  } catch (SQLException e) {
		    System.out.println("Error dropping off parcel: " + e.getMessage());
		    return 0;
		  }
		}
	
	

	
	///////////////////////////////////////////YOU WONT NEED ANYTHING BELOW THAT///////////////////////
	
	
	
	
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
	
	
	
	
	//cannot have all kinds of checks, would be tooooo hectic
	//having some, will try to code carefully
	
	//really important
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
	
	
	
	
	public static boolean isDriverOutForDelivery(int driverID){
	    Statement st = DatabaseConnection.getStatement();
	    Connection conn = DatabaseConnection.getConn();

	    if (statementChecker(st) == 0 || connChecker(conn) == 0) {
	        return false;
	    }

	    try {
	        String query = "SELECT outForDelivery FROM Driver WHERE DID = ?";
	        PreparedStatement ps = conn.prepareStatement(query);
	        ps.setInt(1, driverID);
	        ResultSet rs = ps.executeQuery();

	        if (rs.next()) {
	            return rs.getBoolean("outForDelivery");
	        } else {
	            return false; 
	        }
	    } catch (SQLException e) {
	        System.out.println("Error checking driver's outForDelivery status: " + e.getMessage());
	        return false;
	    } 
	}
	
	
	
	
	/////////////////keep driver removal conditions in mind////////////
	
	
	
	
	
}
