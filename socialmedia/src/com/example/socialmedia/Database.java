
	package com.example.socialmedia;

	import java.sql.Connection;
	import java.sql.DriverManager;
	import java.sql.SQLException;

	public class Database {
	    private static final String DB_URL = "jdbc:mysql://localhost:3306/socialmedia";
	    private static final String DB_USER = "root";
	    private static final String DB_PASSWORD = "abimanyu20@";
	    
	    // OOP Concept: Static Method

	    public static Connection getConnection() {
	        Connection connection = null;
	        try {
	            // OOP Concept: Polymorphism (Class.forName is used to load the JDBC driver class)
	            Class.forName("com.mysql.cj.jdbc.Driver");
	            // OOP Concept: Encapsulation (Private constants are accessed within the class only)
	            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
	            System.out.println("Connected to the database!");
	        } catch (ClassNotFoundException | SQLException e) {
	            e.printStackTrace();
	        }
	        return connection;
	    }
	}

