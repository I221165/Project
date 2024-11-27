package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
	
    private static final String URL = "jdbc:postgresql://localhost:5432/Project";
    private static final String USER = "postgres";
    private static final String PASSWORD = "0312";

    public static Connection connect() throws SQLException {
    	
    	try {
    		Class.forName("org.postgresql.Driver");
    	} catch (ClassNotFoundException e) {
    	    System.out.println("PostgreSQL JDBC Driver not found.");
    	    e.printStackTrace();
    	}

    	
    	
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
