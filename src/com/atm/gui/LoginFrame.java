package com.atm.gui;

import javax.swing.*;
import java.awt.*;
import com.atm.dao.UserDAO;

public class LoginFrame extends JFrame {

	private JTextField cardNumberField;
	private JPasswordField pinField;
	private JButton loginButton;
	private UserDAO userDAO;

	public LoginFrame() {
		super("ATM Login");
		userDAO = new UserDAO();

		// Use custom gradient panel as content pane
		JPanel mainPanel = ThemeUtils.createGradientPanel();
		mainPanel.setLayout(new GridBagLayout()); // Center everything
		setContentPane(mainPanel);

		// --- Create Login Card ---
		JPanel cardPanel = ThemeUtils.createCardPanel();
		GridBagConstraints gbc = new GridBagConstraints();

		// 1. Title
		JLabel titleLabel = new JLabel("ATM Login");
		titleLabel.setFont(ThemeUtils.FONT_TITLE);
		titleLabel.setForeground(ThemeUtils.PRIMARY_COLOR);
		titleLabel.setHorizontalAlignment(SwingConstants.CENTER);

		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 2;
		gbc.insets = new Insets(0, 0, 20, 0); // Bottom padding
		gbc.fill = GridBagConstraints.HORIZONTAL;
		cardPanel.add(titleLabel, gbc);

		// 2. Card Number Row
		JLabel cardLabel = new JLabel("Card Number");
		cardLabel.setFont(ThemeUtils.FONT_NORMAL);
		cardLabel.setForeground(ThemeUtils.TEXT_COLOR);

		gbc.gridy = 1;
		gbc.gridwidth = 1;
		gbc.insets = new Insets(5, 5, 5, 5);
		cardPanel.add(cardLabel, gbc);

		cardNumberField = new JTextField(15);
		ThemeUtils.styleTextField(cardNumberField);

		gbc.gridx = 1;
		cardPanel.add(cardNumberField, gbc);

		// 3. PIN Row
		JLabel pinLabel = new JLabel("PIN");
		pinLabel.setFont(ThemeUtils.FONT_NORMAL);
		pinLabel.setForeground(ThemeUtils.TEXT_COLOR);

		gbc.gridx = 0;
		gbc.gridy = 2;
		cardPanel.add(pinLabel, gbc);

		pinField = new JPasswordField(15);
		ThemeUtils.styleTextField(pinField);

		gbc.gridx = 1;
		cardPanel.add(pinField, gbc);

		// 4. Spacer
		gbc.gridx = 0;
		gbc.gridy = 3;
		gbc.gridwidth = 2;
		gbc.insets = new Insets(15, 0, 0, 0);
		cardPanel.add(Box.createVerticalStrut(10), gbc);

		// 5. Login Button
		loginButton = new JButton("LOGIN");
		ThemeUtils.styleButton(loginButton);

		gbc.gridy = 4;
		gbc.fill = GridBagConstraints.HORIZONTAL; // Stretch button
		cardPanel.add(loginButton, gbc);

		// Add Card to Main Panel
		mainPanel.add(cardPanel);

		// --- Event Listener ---
		java.awt.event.ActionListener loginAction = e -> {
			String cardNumber = cardNumberField.getText();
			String pin = new String(pinField.getPassword());

			if (userDAO.validateUser(cardNumber, pin)) {
				JOptionPane.showMessageDialog(LoginFrame.this, "✅ Login successful!");
				new MainMenuFrame(cardNumber);
				dispose();
			} else {
				JOptionPane.showMessageDialog(LoginFrame.this, "❌ Invalid card number or PIN.", "Error",
						JOptionPane.ERROR_MESSAGE);
			}
		};

		loginButton.addActionListener(loginAction);

		// UX Enhancements
		ThemeUtils.setupNumericInputOnly(cardNumberField);
		ThemeUtils.setupNumericInputOnly(pinField);
		ThemeUtils.addEnterKeyAction(pinField, loginAction);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(450, 400); // Slightly larger
		setLocationRelativeTo(null);
		setVisible(true);
	}

	public static void main(String[] args) {
		// Set Nimbus Look and Feel before creating UI
		try {
			for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
				if ("Nimbus".equals(info.getName())) {
					UIManager.setLookAndFeel(info.getClassName());
					break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		SwingUtilities.invokeLater(LoginFrame::new);
	}
}
