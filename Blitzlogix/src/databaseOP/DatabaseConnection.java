package databaseOP;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseConnection {
	private static DatabaseConnection instance;
	public Connection conn;
	public Statement st;
	public ResultSet S;
	
	private DatabaseConnection() {
		try {
			Class.forName("org.postgresql.Driver");
			conn= DriverManager.getConnection("jdbc:postgresql://localhost:5432/Project","postgres","1234");
			st = conn.createStatement();
			System.out.println("Database connected"); //load data into arrays
		} catch(ClassNotFoundException c) {
			System.out.println("Error class not found");
		} catch(SQLException e) {
			System.out.println("Error Connection not succeeded");
		}
	}
	
	//needs statement to execute only
	public static Statement getStatement() {
		if(instance == null) {
			instance = new DatabaseConnection();
		}
		
		return instance.st;
	}
	//for prepared statement
	public static Connection getConn() {
		if(instance == null) {
			instance = new DatabaseConnection();
		}
		
		return instance.conn;
	}
	
}


