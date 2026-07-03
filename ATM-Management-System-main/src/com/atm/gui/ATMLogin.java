package com.atm.gui;

import javax.swing.*;
import java.sql.*;

public class ATMLogin extends JFrame {

	private JTextField cardField;
	private JPasswordField pinField;

	public ATMLogin() {
		setTitle("ATM Login");
		setSize(300, 200);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setLayout(null);

		JLabel cardLabel = new JLabel("Card Number:");
		cardLabel.setBounds(30, 30, 100, 25);
		add(cardLabel);

		cardField = new JTextField();
		cardField.setBounds(130, 30, 120, 25);
		add(cardField);

		JLabel pinLabel = new JLabel("PIN:");
		pinLabel.setBounds(30, 70, 100, 25);
		add(pinLabel);

		pinField = new JPasswordField();
		pinField.setBounds(130, 70, 120, 25);
		add(pinField);

		JButton loginBtn = new JButton("Login");
		loginBtn.setBounds(90, 110, 100, 30);
		add(loginBtn);

		loginBtn.addActionListener(e -> loginAction());

		setVisible(true);
	}

	private void loginAction() {
		String cardNumber = cardField.getText();
		String pin = new String(pinField.getPassword());

		try (Connection conn = com.atm.db.DBManager.getConnection()) {
			String query = "SELECT * FROM users WHERE card_number=? AND pin=?";
			PreparedStatement pst = conn.prepareStatement(query);
			pst.setString(1, cardNumber);
			pst.setString(2, pin);
			ResultSet rs = pst.executeQuery();

			if (rs.next()) {
				JOptionPane.showMessageDialog(this, "✅ Login Successful");
				// Proceed to dashboard
			} else {
				JOptionPane.showMessageDialog(this, "❌ Invalid Card or PIN");
			}

		} catch (Exception ex) {
			ex.printStackTrace();
			JOptionPane.showMessageDialog(this, "❌ Database error");
		}
	}

	public static void main(String[] args) {
		new ATMLogin();
	}
}
