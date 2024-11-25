package databaseOP;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import model.ParcelHolder;
import model.ParcelStatus;

public class ParcelOP {
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
	
	
	public static int createParcel(int senderID, int receiverID, double weight, int routeID) {
		Statement st = DatabaseConnection.getStatement();
	    Connection conn = DatabaseConnection.getConn();
	    if (statementChecker(st) == 0 || connChecker(conn) == 0) {
	        return 0;
	    }

	    try {
	        String insertQuery = "INSERT INTO Parcel (sender_id, receiver_id, weight, route_id, status, holder, holder_id) VALUES (?, ?, ?, ?, ?, ?, ?)";
	        PreparedStatement ps = conn.prepareStatement(insertQuery);
	        ps.setInt(1, senderID);
	        ps.setInt(2, receiverID);
	        ps.setDouble(3, weight);
	        ps.setInt(4, routeID);
	        ps.setString(5, ParcelStatus.REQUEST_INITIATED.name());
	        ps.setString(6, ParcelHolder.CUSTOMER.name());
	        ps.setInt(7, senderID);
	        ps.executeUpdate();

	        return 1; // Parcel created successfully
	    } catch (SQLException e) {
	        System.out.println("Error creating parcel: " + e.getMessage());
	        return 0; // Failure
	    }
	}
	
	
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
	            case REQUEST_INITIATED:
	                nextStatus = ParcelStatus.ASSIGNED_TO_DRIVER.name();
	                holder = "CUSTOMER";
	                break;
	            case ASSIGNED_TO_DRIVER:
	                nextStatus = ParcelStatus.PICKED_FROM_SENDER.name();
	                holder = "DRIVER";
	                break;
	            case PICKED_FROM_SENDER:
	                nextStatus = ParcelStatus.DROPPED_AT_LOCALCENTER.name();
	                holder = "CENTER";
	                break;
	            case DROPPED_AT_LOCALCENTER:
	                // Assuming intracity for now
	                nextStatus = ParcelStatus.IN_TRANSIT.name();
	                holder = "DRIVER";
	                break;
	            case IN_TRANSIT:
	                // Assuming intracity for now
	                nextStatus = ParcelStatus.AT_DESTINATION_CENTER.name();
	                holder = "CENTER";
	                break;
	            case AT_DESTINATION_CENTER:
	                nextStatus = ParcelStatus.OUT_FOR_DELIVERY.name();
	                holder = "DRIVER";
	                break;
	            case OUT_FOR_DELIVERY:
	                nextStatus = ParcelStatus.DELIVERED.name();
	                holder = "CUSTOMER";
	                break;
	            case DELIVERED:
	                return 0; // Already delivered
	        }

	        String updateQuery = "UPDATE Parcel SET status = ?, holder = ? WHERE parcel_id = ?";
	        ps = conn.prepareStatement(updateQuery);
	        ps.setString(1, nextStatus);
	        ps.setString(2, holder);
	        ps.setInt(3, parcelID);
	        ps.executeUpdate();

	        return 1; 
	    } catch (SQLException e) {
	        System.out.println("Error moving parcel status: " + e.getMessage());
	        return 0;
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
	
	
	
	
	

}
