package com.atm.db;

import java.sql.Connection;
import java.sql.Connection.*;

public class TestConnection {
	public static void main(String[] args) {
		try {
			Connection con = DBManager.getConnection();
			if (con != null) {
				System.out.println("Database connection successful!");
				con.close();
			}
		} catch (Exception e) {
			System.out.println("Failed to connect to database.");
			e.printStackTrace();
		}
	}
}
