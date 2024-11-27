package databaseOP;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class UserOP {
	
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

	public static int makeUser(String cnic,String name,String phone,String email) {
		Statement st = DatabaseConnection.getStatement();
		if(statementChecker(st) == 0) {
		   return 0;	
		}
		
		try {
			
	        if (searchUser(cnic) > 0) {
	            return 1; // CNIC already exists, return success
	        }
			
			String query ="insert into userdetails values('"+cnic+"','"+name+"','"+phone+"','"+email+"')";
			st.executeUpdate(query);
			return 1; 
		}catch(SQLException e) {
			System.out.println("Error saving data in database");
			return 0;//error boi
		}		
    }
	
	
	
	public static int searchUser(String cnic) {
	    Statement st = DatabaseConnection.getStatement();
	    Connection conn = DatabaseConnection.getConn();
	    if (statementChecker(st) == 0 || connChecker(conn) == 0) {
	        return 0;
	    }

	    try {
	        String checkQuery = "SELECT COUNT(*) FROM userdetails WHERE cnic = ?";
	        PreparedStatement ps = conn.prepareStatement(checkQuery);
	        ps.setString(1, cnic);
	        ResultSet rs = ps.executeQuery();
	        rs.next();
	        int count = rs.getInt(1);

	        rs.close();
	        ps.close();

	        return count; 
	    } catch (SQLException e) {
	        System.out.println("Error searching user: " + e.getMessage());
	        return 0; 
	    }
	}
	 
}
