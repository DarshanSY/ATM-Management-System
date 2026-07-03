package com.atm.gui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import com.atm.dao.UserDAO;

public class MainMenuFrame extends JFrame {

	private String cardNumber;
	private UserDAO userDAO = new UserDAO();
	private Timer logoutTimer;
	private static final int TIMEOUT = 2 * 60 * 1000; // 2 minutes

	public MainMenuFrame(String cardNumber) {
		super("ATM Main Menu");
		this.cardNumber = cardNumber;

		// === Main Panel with Gradient ===
		JPanel mainPanel = ThemeUtils.createGradientPanel();
		mainPanel.setLayout(new GridBagLayout());
		setContentPane(mainPanel);

		// === Card Panel for Menu ===
		JPanel cardPanel = ThemeUtils.createCardPanel();
		cardPanel.setLayout(new BoxLayout(cardPanel, BoxLayout.Y_AXIS));

		// Welcome Label
		JLabel welcomeLabel = new JLabel("Welcome, Hari Prasad!");
		welcomeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		welcomeLabel.setFont(ThemeUtils.FONT_TITLE);
		welcomeLabel.setForeground(ThemeUtils.PRIMARY_COLOR);

		cardPanel.add(welcomeLabel);
		cardPanel.add(Box.createVerticalStrut(20));

		// === Buttons ===
		String[] buttonLabels = { "💸 Withdraw", "💰 Deposit", "🏦 Check Balance", "📜 Transaction History",
				"🔒 Change PIN",
				"🚪 Logout" };

		JButton[] buttons = new JButton[buttonLabels.length];

		for (int i = 0; i < buttonLabels.length; i++) {
			buttons[i] = new JButton(buttonLabels[i]);
			ThemeUtils.styleButton(buttons[i]);
			buttons[i].setAlignmentX(Component.CENTER_ALIGNMENT);
			buttons[i].setMaximumSize(new Dimension(250, 45)); // Slightly wider and taller

			cardPanel.add(buttons[i]);
			cardPanel.add(Box.createVerticalStrut(10));
		}

		mainPanel.add(cardPanel);

		// === Actions ===
		JButton withdrawButton = buttons[0];
		JButton depositButton = buttons[1];
		JButton balanceButton = buttons[2];
		JButton transactionHistoryButton = buttons[3];
		JButton changePinButton = buttons[4];
		JButton logoutButton = buttons[5];

		// Logout button special styling
		logoutButton.setBackground(new Color(220, 53, 69)); // Red color for logout
		logoutButton.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createLineBorder(new Color(220, 53, 69), 1),
				new EmptyBorder(10, 20, 10, 20)));
		logoutButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				logoutButton.setBackground(new Color(200, 35, 51));
			}

			@Override
			public void mouseExited(MouseEvent e) {
				logoutButton.setBackground(new Color(220, 53, 69));
			}
		});

		balanceButton.addActionListener(e -> {
			double balance = userDAO.getBalance(cardNumber);
			String message = (balance >= 0) ? "Your balance is: ₹ " + balance : "Error retrieving balance.";
			JOptionPane.showMessageDialog(this, message);
			resetLogoutTimer();
		});

		withdrawButton.addActionListener(e -> {
			String input = JOptionPane.showInputDialog(this, "Enter amount to withdraw:");
			if (input != null) {
				try {
					double amount = Double.parseDouble(input);
					if (amount <= 0) {
						JOptionPane.showMessageDialog(this, "Please enter a positive amount.");
						return;
					}
					boolean success = userDAO.withdrawAmount(cardNumber, amount);
					JOptionPane.showMessageDialog(this, success ? "✅ Withdraw successful. Amount: ₹ " + amount
							: "❌ Insufficient balance or error occurred.");
				} catch (NumberFormatException ex) {
					JOptionPane.showMessageDialog(this, "❌ Invalid amount entered.");
				}
			}
			resetLogoutTimer();
		});

		depositButton.addActionListener(e -> {
			String input = JOptionPane.showInputDialog(this, "Enter amount to deposit:");
			if (input != null) {
				try {
					double amount = Double.parseDouble(input);
					if (amount <= 0) {
						JOptionPane.showMessageDialog(this, "Please enter a positive amount.");
						return;
					}
					boolean success = userDAO.depositAmount(cardNumber, amount);
					JOptionPane.showMessageDialog(this,
							success ? "✅ Deposit successful. Amount: ₹ " + amount : "❌ Error occurred during deposit.");
				} catch (NumberFormatException ex) {
					JOptionPane.showMessageDialog(this, "❌ Invalid amount entered.");
				}
			}
			resetLogoutTimer();
		});

		transactionHistoryButton.addActionListener(e -> {
			new TransactionHistoryFrame(cardNumber);
			resetLogoutTimer();
		});

		changePinButton.addActionListener(e -> {
			new ChangePinFrame(cardNumber);
			resetLogoutTimer();
		});

		logoutButton.addActionListener(e -> {
			logout();
		});

		// === Logout timer ===
		logoutTimer = new Timer(TIMEOUT, e -> {
			JOptionPane.showMessageDialog(this, "Session timed out due to inactivity.");
			logout();
		});
		logoutTimer.setRepeats(false);
		logoutTimer.start();

		addUserActivityListeners();

		setSize(400, 550); // Adjusted size
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}

	private void logout() {
		dispose();
		new LoginFrame();
	}

	private void resetLogoutTimer() {
		if (logoutTimer != null) {
			logoutTimer.restart();
		}
	}

	private void addUserActivityListeners() {
		for (Component comp : getContentPane().getComponents()) {
			if (comp instanceof JButton) {
				((JButton) comp).addActionListener(e -> resetLogoutTimer());
			}
		}
		this.addMouseMotionListener(new MouseMotionAdapter() {
			public void mouseMoved(MouseEvent e) {
				resetLogoutTimer();
			}
		});
		this.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				resetLogoutTimer();
			}
		});
		this.setFocusable(true);
		this.requestFocusInWindow();
	}
}
