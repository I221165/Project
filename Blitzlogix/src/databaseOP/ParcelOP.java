package databaseOP;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import model.ParcelHolder;
import model.ParcelStatus;

public class ParcelOP {
	
	
	////////////////////////////creation//////////////////////////////////////////////////
	
	public static int createParcel(int senderID, int receiverID, double weight, int routeID) {
		Statement st = DatabaseConnection.getStatement();
	    Connection conn = DatabaseConnection.getConn();
	    if (statementChecker(st) == 0 || connChecker(conn) == 0) {
	        return 0;
	    }

	    try {
	    	String cityName = RouteOP.getSourceCityFromRouteID(routeID);
	    	int centerID = CenterOP.getCenterIDByCity(cityName);
	    	
	        String insertQuery = "INSERT INTO Parcel (sender_id, receiver_id, weight, route_id, status, holder, holder_id) VALUES (?, ?, ?, ?, ?, ?, ?)";
	        PreparedStatement ps = conn.prepareStatement(insertQuery);
	        ps.setInt(1, senderID);
	        ps.setInt(2, receiverID);
	        ps.setDouble(3, weight);
	        ps.setInt(4, routeID);
	        ps.setString(5, ParcelStatus.REQUEST_INITIATED.name());
	        ps.setString(6, ParcelHolder.CENTER.name()); //who has to display for assignment
	        ps.setInt(7, centerID);
	        ps.executeUpdate();

	        String maxParcelIDQuery = "SELECT MAX(parcel_id) FROM Parcel";
	        PreparedStatement maxParcelIDPs = conn.prepareStatement(maxParcelIDQuery);
	        ResultSet rs = maxParcelIDPs.executeQuery();

	        int maxParcelID = 0;
	        if (rs.next()) {
	            maxParcelID = rs.getInt(1);
	        }
	        
	        
	        
	        
	        //now increments the count, yeaahhh Fascade can be made 
	        CustomerOP.incrementCustomerParcelSent(senderID);
	        PaymentOP.insertPayment(senderID, maxParcelID); //payment due
	        ParcelTrackingOP.updateParcelTracking(maxParcelID);
	        
	        
	        
	        
	        
	        return maxParcelID; // Parcel created successfully
	    } catch (SQLException e) {
	        System.out.println("Error creating parcel: " + e.getMessage());
	        return 0; // Failure
	    }
	}

	
	
	
	////////////////YOU WONT NEED ANYTHING BELOW THAT///////////////////////////////////
	
	
	
	
	
	
	
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
	
	///////////////////////////////////////////attributesgetter///////////////////////////////
	
	
	
	public static int getSenderIDFromParcelID(int parcelID) {
	    Statement st = DatabaseConnection.getStatement();
	    Connection conn = DatabaseConnection.getConn();
	    if (statementChecker(st) == 0 || connChecker(conn) == 0) {
	        return 0;
	    }

	    try {
	        String selectQuery = "SELECT sender_id FROM Parcel WHERE parcel_id = ?";
	        PreparedStatement ps = conn.prepareStatement(selectQuery);
	        ps.setInt(1, parcelID);
	        ResultSet rs = ps.executeQuery();

	        if (rs.next()) {
	            int senderID = rs.getInt(1);
	            rs.close();
	            ps.close();
	            return senderID;
	        } else {
	            rs.close();
	            ps.close();
	            return 0; // Parcel not found
	        }
	    } catch (SQLException e) {
	        System.out.println("Error fetching sender ID: " + e.getMessage());
	        return -1; // Error occurred
	    }
	}
	
	
	
	public static int getReceiverIDFromParcelID(int parcelID) {
	    Statement st = DatabaseConnection.getStatement();
	    Connection conn = DatabaseConnection.getConn();
	    if (statementChecker(st) == 0 || connChecker(conn) == 0) {
	        return 0;
	    }

	    try {
	        String selectQuery = "SELECT receiver_id FROM Parcel WHERE parcel_id = ?";
	        PreparedStatement ps = conn.prepareStatement(selectQuery);
	        ps.setInt(1, parcelID);
	        ResultSet rs = ps.executeQuery();

	        if (rs.next()) {
	            int receiverID = rs.getInt(1);
	            rs.close();
	            ps.close();
	            return receiverID;
	        } else {
	            rs.close();
	            ps.close();
	            return 0; // Parcel not found
	        }
	    } catch (SQLException e) {
	        System.out.println("Error fetching receiver ID: " + e.getMessage());
	        return -1; // Error occurred
	    }
	}
		
	
	
	
	
	//route id to get address further
	
		public static int getRouteIDFromParcelID(int parcelID) {
			Statement st = DatabaseConnection.getStatement();
		    Connection conn = DatabaseConnection.getConn();
		    if (statementChecker(st) == 0 || connChecker(conn) == 0) {
		        return 0;
		    }

		    try {
		        String selectQuery = "SELECT route_id FROM Parcel WHERE parcel_id = ?";
		        PreparedStatement ps = conn.prepareStatement(selectQuery);
		        ps.setInt(1, parcelID);
		        ResultSet rs = ps.executeQuery();

		        if (rs.next()) {
		            int routeID = rs.getInt(1);
		            rs.close();
		            ps.close();
		            return routeID;
		        } else {
		            rs.close();
		            ps.close();
		            return 0; 
		        }
		    } catch (SQLException e) {
		        System.out.println("Error fetching route ID: " + e.getMessage());
		        return -1; // Error occurred
		    }
		}
	
	////////////////////////////////source and destination city getters /////////////////
	
		
		public static String getSourceCityFromParcelID(int parcelID) {
		    Statement st = DatabaseConnection.getStatement();
		    Connection conn = DatabaseConnection.getConn();
		    if (statementChecker(st) == 0 || connChecker(conn) == 0) {
		        return "Address not found";
		    }

		    try {
		        String selectQuery = "SELECT a.city FROM Parcel p JOIN Route r ON p.route_id = r.route_id JOIN Address a ON r.source_id = a.id WHERE p.parcel_id = ?";
		        PreparedStatement ps = conn.prepareStatement(selectQuery);
		        ps.setInt(1, parcelID);
		        ResultSet rs = ps.executeQuery();

		        if (rs.next()) {
		            String city = rs.getString("city");
		            return city;
		        } else {
		            return "Address not found"; // Parcel not found or error fetching source city
		        }
		    } catch (SQLException e) {
		        System.out.println("Error fetching source city: " + e.getMessage());
		        return "Address not found";
		    }
		}
		
		
		
		
		
		public static String getDestinationCityFromParcelID(int parcelID) {
		    Statement st = DatabaseConnection.getStatement();
		    Connection conn = DatabaseConnection.getConn();
		    if (statementChecker(st) == 0 || connChecker(conn) == 0) {
		        return null;
		    }

		    try {
		        String selectQuery = "SELECT a.city FROM Parcel p JOIN Route r ON p.route_id = r.route_id JOIN Address a ON r.destination_id = a.id WHERE p.parcel_id = ?";
		        PreparedStatement ps = conn.prepareStatement(selectQuery);
		        ps.setInt(1, parcelID);
		        ResultSet rs = ps.executeQuery();

		        if (rs.next()) {
		            String city = rs.getString("city");
		            return city;
		        } else {
		            return null; // Parcel not found or error fetching destination city
		        }
		    } catch (SQLException e) {
		        System.out.println("Error fetching destination city: " + e.getMessage());
		        return null;
		    }
		}
		
		
		
		public static int getHolderIDFromParcel(int parcelID){
		    Statement st = DatabaseConnection.getStatement();
		    Connection conn = DatabaseConnection.getConn();

		    if (statementChecker(st) == 0 || connChecker(conn) == 0) {
		        return 0; // Indicate failure
		    }

		    try {
		        String query = "SELECT holder_id FROM Parcel WHERE parcel_id = ?";
		        PreparedStatement ps = conn.prepareStatement(query);
		        ps.setInt(1, parcelID);
		        ResultSet rs = ps.executeQuery();

		        if (rs.next()) {
		            return rs.getInt("holder_id");
		        } else {
		            return 0; 
		        }
		    } catch (SQLException e) {
		        System.out.println("Error fetching holder ID for parcel: " + e.getMessage());
		        return 0; // Indicate failure
		    }
		}
	
	
	////////////////////////////////////////////creation ///////////////////////////////////////////////
	
	public static int parcelExists(int parcelID) {
		Statement st = DatabaseConnection.getStatement();
	    Connection conn = DatabaseConnection.getConn();
	    if (statementChecker(st) == 0 || connChecker(conn) == 0) {
	        return 0;
	    }
	    if (statementChecker(st) == 0) {
	        return 0;
	    }

	    try {
	        String checkQuery = "SELECT COUNT(*) FROM Parcel WHERE parcel_id = ?";
	        PreparedStatement ps = conn.prepareStatement(checkQuery);
	        ps.setInt(1, parcelID);
	        ResultSet rs = ps.executeQuery();

	        if (rs.next()) {
	            int count = rs.getInt(1);
	            if(count > 0) {
	            	return 1;
	            }
	            else {
	            	return 0;
	            }
 	        } else {
	            return 0;
	        }
	    } catch (SQLException e) {
	        System.out.println("Error checking parcel existence: " + e.getMessage());
	        return 0;
	    }
	}
	
	
	
	
	
	/////////////////////////////////////////creation//////////////////////////////
	
	
	//while making, using data you make 2 addressses first then make a route
	//then use that route ID here
	
	
	
	/////////////////////important consider many things///////////////////////////////////
	
	public static int moveStatusForward(int parcelID) {
		Statement st = DatabaseConnection.getStatement();
	    Connection conn = DatabaseConnection.getConn();
	    if (statementChecker(st) == 0 || connChecker(conn) == 0) {
	        return 0;
	    }
	    try {
	    	if (parcelExists(parcelID) == 0) {
	            return 0; // Parcel not found
	        }
	    	
	        String selectQuery = "SELECT status FROM Parcel WHERE parcel_id = ?";
	        PreparedStatement ps = conn.prepareStatement(selectQuery);
	        ps.setInt(1, parcelID);
	        ResultSet rs = ps.executeQuery();

	        if (!rs.next()) {
	            rs.close();
	            ps.close();
	            return 0; // Parcel not found
	        }

	        String currentStatus = rs.getString(1);
	        rs.close();
	        ps.close();

	        String nextStatus = null;
	        String holder = null;

	        switch (ParcelStatus.valueOf(currentStatus)) {
	            case REQUEST_INITIATED: //was in center
	                nextStatus = ParcelStatus.ASSIGNED_TO_DRIVER.name();   //piclup
	                holder = "DRIVER";
	                break;
	            case ASSIGNED_TO_DRIVER:
	                nextStatus = ParcelStatus.PICKED_FROM_SENDER.name();  //drop off
	                holder = "DRIVER";
	                break;
	            case PICKED_FROM_SENDER:
	                nextStatus = ParcelStatus.DROPPED_AT_LOCALCENTER.name(); //to assign
	                holder = "CENTER";
	                break;
	            case DROPPED_AT_LOCALCENTER:
	                // Assuming intracity for now
	                nextStatus = ParcelStatus.ASSIGNED_TRANSIT.name(); //pickup
	                holder = "DRIVER";
	                break;
	            case ASSIGNED_TRANSIT:
	                // Assuming intracity for now
	                nextStatus = ParcelStatus.IN_TRANSIT.name(); //dropoff
	                holder = "DRIVER";
	                break;
	            case IN_TRANSIT:
	                // Assuming intracity for now
	                nextStatus = ParcelStatus.AT_DESTINATION_CENTER.name(); //center to assign local
	                holder = "CENTER";
	                break;
	            case AT_DESTINATION_CENTER:
	                nextStatus = ParcelStatus.ASSIGNED_DELIVERY.name(); //driver to pickup
	                holder = "DRIVER";
	                break;
	            case ASSIGNED_DELIVERY:
	                nextStatus = ParcelStatus.OUT_FOR_DELIVERY.name(); //driver to dropoff
	                holder = "DRIVER";
	                break;
	            case OUT_FOR_DELIVERY:
	                nextStatus = ParcelStatus.DELIVERED.name(); //done
	                holder = "CUSTOMER";
	                break;
	            case DELIVERED:
	            	nextStatus = ParcelStatus.FINISHED.name(); //done
	                holder = "CUSTOMER";
	                break; // Already delivered
	            case FINISHED:
	            	return 0;
	        }

	        String updateQuery = "UPDATE Parcel SET status = ?, holder = ? WHERE parcel_id = ?";
	        ps = conn.prepareStatement(updateQuery);
	        ps.setString(1, nextStatus);
	        ps.setString(2, holder);
	        ps.setInt(3, parcelID);
	        ps.executeUpdate();

	        //updating tracking is important here 
	        ParcelTrackingOP.updateParcelTracking(parcelID);
	        
	        return 1; 
	    } catch (SQLException e) {
	        System.out.println("Error moving parcel status: " + e.getMessage());
	        return 0;
	    }
	}
	
	
	
	
	
	
	public static ResultSet getTrackingInfoForCustomer(int customerID){

	    Statement st = DatabaseConnection.getStatement();

	    Connection conn = DatabaseConnection.getConn();



	    if (statementChecker(st) == 0 || connChecker(conn) == 0) {

	        return null;

	    }



	    try {

	        String selectQuery = "SELECT p.parcel_id, s.name AS sender_name, a1.city AS source_city, " +

	                             "r.name AS receiver_name, a2.city AS destination_city, p.status " +

	                             "FROM Parcel p " +

	                             "JOIN Customer s_cust ON p.sender_id = s_cust.CID " +

	                             "JOIN Customer r_cust ON p.receiver_id = r_cust.CID " +

	                             "JOIN UserDetails s ON s_cust.CNIC = s.CNIC " +

	                             "JOIN UserDetails r ON r_cust.CNIC = r.CNIC " +

	                             "JOIN Address a1 ON (SELECT source_id FROM Route WHERE route_id = p.route_id) = a1.id " +

	                             "JOIN Address a2 ON (SELECT destination_id FROM Route WHERE route_id = p.route_id) = a2.id " +

	                             "WHERE p.sender_id = ? OR p.receiver_id = ?";



	        PreparedStatement ps = conn.prepareStatement(selectQuery);

	        ps.setInt(1, customerID);

	        ps.setInt(2, customerID);

	        ResultSet rs = ps.executeQuery();



	        return rs;

	    } catch (SQLException e) {

	        System.out.println("Error fetching tracking info for customer: " + e.getMessage());

	        return null;

	    }

	}
	
	
	
	
	
	
	
	public static int updateParcelHolderID(int parcelID, int newHolderID) {
		Statement st = DatabaseConnection.getStatement();
	    Connection conn = DatabaseConnection.getConn();
	    if (statementChecker(st) == 0 || connChecker(conn) == 0) {
	        return 0;
	    }

	    try {
	        String updateQuery = "UPDATE Parcel SET holder_id = ? WHERE parcel_id = ?";
	        PreparedStatement ps = conn.prepareStatement(updateQuery);
	        ps.setInt(1, newHolderID);
	        ps.setInt(2, parcelID);
	        ps.executeUpdate();

	        return 1; 
	    } catch (SQLException e) {
	        System.out.println("Error updating parcel holder: " + e.getMessage());
	        return 0; 
	    }
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	//////////////////////////////////////getting status for tracking
	
	
	public static String getParcelStatus(int parcelID){
	    Connection conn = DatabaseConnection.getConn();
	    Statement st =  DatabaseConnection.getStatement();
	    if (statementChecker(st) == 0 || connChecker(conn) == 0) {
	        return "error";
	    }
	    try {
	        String query = "SELECT status FROM Parcel WHERE parcel_id = ?";
	        PreparedStatement ps = conn.prepareStatement(query);
	        ps.setInt(1, parcelID);
	        ResultSet rs = ps.executeQuery();

	        if (rs.next()) {
	            return rs.getString("status");
	        } else {
	            return "Parcel not found";
	        }
	    } catch (SQLException e) {
	        System.out.println("Error fetching parcel status: " + e.getMessage());
	    }
	    
	    return "error";
	}
	
	

}
