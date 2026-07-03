package com.atm.gui;

import com.atm.dao.UserDAO;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TransactionHistoryFrame extends JFrame {

    private String cardNumber;
    private UserDAO userDAO = new UserDAO();

    public TransactionHistoryFrame(String cardNumber) {
        super("Transaction History");
        this.cardNumber = cardNumber;

        // Use custom gradient panel as content pane
        JPanel mainPanel = ThemeUtils.createGradientPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        setContentPane(mainPanel);

        // Header Label
        JLabel titleLabel = new JLabel("Transaction History");
        titleLabel.setFont(ThemeUtils.FONT_TITLE);
        titleLabel.setForeground(Color.WHITE); // White text on gradient
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setBorder(new EmptyBorder(0, 0, 20, 0));
        mainPanel.add(titleLabel, BorderLayout.NORTH);

        // Table columns
        String[] columns = { "Transaction Type", "Amount (₹)", "Date & Time" };

        // Table model
        DefaultTableModel model = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        JTable table = new JTable(model);
        table.setFont(ThemeUtils.FONT_NORMAL);
        table.setRowHeight(30);
        table.setSelectionBackground(ThemeUtils.SECONDARY_COLOR);
        table.setSelectionForeground(Color.WHITE);
        table.setShowGrid(false);
        table.setIntercellSpacing(new Dimension(0, 0));

        // Header Styling
        JTableHeader header = table.getTableHeader();
        header.setFont(ThemeUtils.FONT_BOLD);
        header.setBackground(ThemeUtils.PRIMARY_COLOR);
        header.setForeground(Color.WHITE);
        header.setPreferredSize(new Dimension(0, 40));

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.getViewport().setBackground(Color.WHITE);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());

        mainPanel.add(scrollPane, BorderLayout.CENTER);

        // Close Button
        JButton closeButton = new JButton("Close");
        ThemeUtils.styleButton(closeButton);
        closeButton.addActionListener(e -> dispose());

        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false);
        buttonPanel.setBorder(new EmptyBorder(15, 0, 0, 0));
        buttonPanel.add(closeButton);

        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        // Fetch transaction data and add to model
        try (ResultSet rs = userDAO.getTransactionHistory(cardNumber)) {
            while (rs != null && rs.next()) {
                String type = rs.getString("transaction_type");
                double amount = rs.getDouble("amount");
                String date = rs.getString("transaction_date");

                model.addRow(new Object[] { type, String.format("%.2f", amount), date });
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error fetching transaction history.", "Error",
                    JOptionPane.ERROR_MESSAGE);
        }

        setSize(600, 450);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible(true);
    }
}
