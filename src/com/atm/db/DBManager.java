package com.atm.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBManager {

	private static final String URL = "jdbc:mysql://localhost:3306/atmdb"; // Replace with your DB name
	private static final String USER = "root"; // Replace with your MySQL username
	private static final String PASSWORD = "root"; // Replace with your MySQL password

	public static Connection getConnection() throws SQLException {
		return DriverManager.getConnection(URL, USER, PASSWORD);
	}

	public static void main(String[] args) {
		try (Connection conn = getConnection()) {
			System.out.println("✅ Connection successful!");
		} catch (SQLException e) {
			System.out.println("❌ Failed to connect to the database.");
			e.printStackTrace();
		}
	}
}
	