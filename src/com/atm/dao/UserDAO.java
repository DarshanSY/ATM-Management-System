package com.atm.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import com.atm.db.DBManager;

public class UserDAO {

	// Validates user login credentials against the database
	public boolean validateUser(String cardNumber, String pin) {
		String sql = "SELECT * FROM users WHERE card_number = ? AND pin = ?";

		try (Connection conn = DBManager.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

			ps.setString(1, cardNumber);
			ps.setString(2, pin);

			try (ResultSet rs = ps.executeQuery()) {
				return rs.next(); // true if user exists
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return false;
	}

	public double getBalance(String cardNumber) {
		String sql = "SELECT balance FROM users WHERE card_number = ?";
		try (Connection conn = DBManager.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

			ps.setString(1, cardNumber);
			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					return rs.getDouble("balance");
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return -1; // Return -1 if error or no user found
	}

	public boolean withdrawAmount(String cardNumber, double amount) {
		String checkSql = "SELECT balance FROM users WHERE card_number = ?";
		String updateSql = "UPDATE users SET balance = balance - ? WHERE card_number = ?";

		try (Connection conn = DBManager.getConnection();
				PreparedStatement checkStmt = conn.prepareStatement(checkSql);
				PreparedStatement updateStmt = conn.prepareStatement(updateSql)) {

			// Check current balance
			checkStmt.setString(1, cardNumber);
			try (ResultSet rs = checkStmt.executeQuery()) {
				if (rs.next()) {
					double currentBalance = rs.getDouble("balance");
					if (currentBalance < amount) {
						return false; // insufficient funds
					}
				} else {
					return false; // user not found
				}
			}

			// Deduct amount
			updateStmt.setDouble(1, amount);
			updateStmt.setString(2, cardNumber);
			int rowsUpdated = updateStmt.executeUpdate();

			if (rowsUpdated > 0) {
				// Log the transaction after successful update
				addTransaction(cardNumber, "Withdrawal", amount);
				return true;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean depositAmount(String cardNumber, double amount) {
		if (amount <= 0) {
			return false; // Invalid deposit amount
		}

		String sql = "UPDATE users SET balance = balance + ? WHERE card_number = ?";
		try (Connection conn = DBManager.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

			ps.setDouble(1, amount);
			ps.setString(2, cardNumber);
			int rowsUpdated = ps.executeUpdate();

			if (rowsUpdated > 0) {
				// Log the transaction after successful update
				addTransaction(cardNumber, "Deposit", amount);
				return true;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean addTransaction(String cardNumber, String type, double amount) {
		String sql = "INSERT INTO transactions (card_number, transaction_type, amount) VALUES (?, ?, ?)";
		try (Connection conn = DBManager.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

			ps.setString(1, cardNumber);
			ps.setString(2, type);
			ps.setDouble(3, amount);
			int rowsInserted = ps.executeUpdate();
			return rowsInserted > 0;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public ResultSet getTransactionHistory(String cardNumber) {
		String sql = "SELECT transaction_type, amount, transaction_date FROM transactions WHERE card_number = ? ORDER BY transaction_date DESC";
		try {
			Connection conn = DBManager.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, cardNumber);
			return ps.executeQuery(); // Caller must handle closing this ResultSet
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	// Rename updatePin to changePin for consistency with ChangePinFrame
	public boolean changePin(String cardNumber, String newPin) {
		String sql = "UPDATE users SET pin = ? WHERE card_number = ?";
		try (Connection conn = DBManager.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setString(1, newPin);
			ps.setString(2, cardNumber);
			int rowsUpdated = ps.executeUpdate();
			return rowsUpdated > 0;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
}
