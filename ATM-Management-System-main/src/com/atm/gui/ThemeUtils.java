package com.atm.gui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ThemeUtils {

    // Colors
    public static final Color PRIMARY_COLOR = new Color(0, 80, 158); // Deep Blue
    public static final Color SECONDARY_COLOR = new Color(0, 168, 232); // Cyan Blue
    public static final Color ACCENT_COLOR = new Color(255, 193, 7); // Amber/Gold
    public static final Color TEXT_COLOR = new Color(33, 37, 41); // Dark Gray
    public static final Color BACKGROUND_GRADIENT_START = new Color(2, 0, 36);
    public static final Color BACKGROUND_GRADIENT_END = new Color(9, 9, 121);
    public static final Color CARD_BACKGROUND = new Color(255, 255, 255, 240); // Translucent White

    // Fonts
    public static final Font FONT_TITLE = new Font("Segoe UI", Font.BOLD, 22);
    public static final Font FONT_SUBTITLE = new Font("Segoe UI", Font.PLAIN, 16);
    public static final Font FONT_NORMAL = new Font("Segoe UI", Font.PLAIN, 14);
    public static final Font FONT_BOLD = new Font("Segoe UI", Font.BOLD, 14);

    /**
     * Creates a JPanel with a gradient background.
     */
    public static JPanel createGradientPanel() {
        return new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
                int w = getWidth();
                int h = getHeight();
                GradientPaint gp = new GradientPaint(0, 0, BACKGROUND_GRADIENT_START, w, h, SECONDARY_COLOR);
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, w, h);
            }
        };
    }

    /**
     * Styles a standard JButton with a modern look.
     */
    public static void styleButton(JButton button) {
        button.setFont(FONT_BOLD);
        button.setBackground(PRIMARY_COLOR);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(PRIMARY_COLOR, 1),
                new EmptyBorder(10, 20, 10, 20)));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Hover Effect
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(SECONDARY_COLOR);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(PRIMARY_COLOR);
            }
        });
    }

    /**
     * Styles a text field (JTextField / JPasswordField)
     */
    public static void styleTextField(JTextField field) {
        field.setFont(FONT_NORMAL);
        field.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
                new EmptyBorder(5, 10, 5, 10)));
    }

    /**
     * Creates a white "Card" panel to hold form elements.
     */
    public static JPanel createCardPanel() {
        JPanel card = new JPanel();
        card.setBackground(CARD_BACKGROUND);
        card.setLayout(new GridBagLayout());
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(255, 255, 255, 100), 1, true),
                new EmptyBorder(20, 30, 20, 30) // Inner padding
        ));
        return card;
    }

    /**
     * Restricts a JTextField to accept only numeric input.
     */
    public static void setupNumericInputOnly(JTextField field) {
        field.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent e) {
                char c = e.getKeyChar();
                if (!((c >= '0') && (c <= '9') ||
                        (c == java.awt.event.KeyEvent.VK_BACK_SPACE) ||
                        (c == java.awt.event.KeyEvent.VK_DELETE))) {
                    e.consume();
                }
            }
        });
    }

    /**
     * Binds the Enter key on a component to trigger an ActionListener.
     */
    public static void addEnterKeyAction(JComponent component, java.awt.event.ActionListener action) {
        component.registerKeyboardAction(action,
                KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_ENTER, 0),
                JComponent.WHEN_FOCUSED);
    }
}
