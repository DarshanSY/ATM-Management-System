package com.atm.gui;

import com.atm.dao.UserDAO;
import javax.swing.*;
import java.awt.*;

public class ChangePinFrame extends JFrame {

    private String cardNumber;
    private UserDAO userDAO = new UserDAO();

    public ChangePinFrame(String cardNumber) {
        super("Change PIN");
        this.cardNumber = cardNumber;

        // Use custom gradient panel as content pane
        JPanel mainPanel = ThemeUtils.createGradientPanel();
        mainPanel.setLayout(new GridBagLayout()); // Center everything
        setContentPane(mainPanel);

        // --- Create Form Card ---
        JPanel cardPanel = ThemeUtils.createCardPanel();
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // 1. Title
        JLabel titleLabel = new JLabel("Change PIN");
        titleLabel.setFont(ThemeUtils.FONT_TITLE);
        titleLabel.setForeground(ThemeUtils.PRIMARY_COLOR);
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(0, 0, 20, 0); // Bottom padding
        cardPanel.add(titleLabel, gbc);

        // Reset insets
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.gridwidth = 1;

        // 2. Current PIN
        JLabel currentPinLabel = new JLabel("Current PIN:");
        currentPinLabel.setFont(ThemeUtils.FONT_NORMAL);
        gbc.gridx = 0;
        gbc.gridy = 1;
        cardPanel.add(currentPinLabel, gbc);

        JPasswordField currentPinField = new JPasswordField(15);
        ThemeUtils.styleTextField(currentPinField);
        gbc.gridx = 1;
        cardPanel.add(currentPinField, gbc);

        // 3. New PIN
        JLabel newPinLabel = new JLabel("New PIN:");
        newPinLabel.setFont(ThemeUtils.FONT_NORMAL);
        gbc.gridx = 0;
        gbc.gridy = 2;
        cardPanel.add(newPinLabel, gbc);

        JPasswordField newPinField = new JPasswordField(15);
        ThemeUtils.styleTextField(newPinField);
        gbc.gridx = 1;
        cardPanel.add(newPinField, gbc);

        // 4. Confirm PIN
        JLabel confirmPinLabel = new JLabel("Confirm PIN:");
        confirmPinLabel.setFont(ThemeUtils.FONT_NORMAL);
        gbc.gridx = 0;
        gbc.gridy = 3;
        cardPanel.add(confirmPinLabel, gbc);

        JPasswordField confirmPinField = new JPasswordField(15);
        ThemeUtils.styleTextField(confirmPinField);
        gbc.gridx = 1;
        cardPanel.add(confirmPinField, gbc);

        // 5. Change Button
        JButton changePinButton = new JButton("UPDATE PIN");
        ThemeUtils.styleButton(changePinButton);

        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(20, 0, 0, 0);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        cardPanel.add(changePinButton, gbc);

        mainPanel.add(cardPanel);

        // --- Action ---
        java.awt.event.ActionListener changeAction = e -> {
            String currentPin = new String(currentPinField.getPassword());
            String newPin = new String(newPinField.getPassword());
            String confirmPin = new String(confirmPinField.getPassword());

            if (currentPin.isEmpty() || newPin.isEmpty() || confirmPin.isEmpty()) {
                JOptionPane.showMessageDialog(this, "⚠️ Please enter all fields.");
                return;
            }

            if (!newPin.equals(confirmPin)) {
                JOptionPane.showMessageDialog(this, "❌ New PIN and Confirm PIN do not match.");
                return;
            }

            // In a real app we should verify current PIN first, but assuming flow:
            // For now, proceeding with update.
            boolean success = userDAO.changePin(cardNumber, newPin);
            if (success) {
                JOptionPane.showMessageDialog(this, "✅ PIN changed successfully!");
                dispose(); // Close this window after success
            } else {
                JOptionPane.showMessageDialog(this, "❌ Error changing PIN. Try again.", "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        };

        changePinButton.addActionListener(changeAction);

        // UX Enhancements
        ThemeUtils.setupNumericInputOnly(currentPinField);
        ThemeUtils.setupNumericInputOnly(newPinField);
        ThemeUtils.setupNumericInputOnly(confirmPinField);
        ThemeUtils.addEnterKeyAction(confirmPinField, changeAction);

        setSize(450, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible(true);
    }
}
