
        package ui;

import ui.Login;

import javax.swing.*;
import java.awt.*;

public class SettingsFrame extends JFrame {

    public SettingsFrame(int userId) {

        setTitle("Settings - Kuber Manager");
        setSize(500, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(
                BorderFactory.createEmptyBorder(30, 40, 30, 40)
        );
        panel.setBackground(new Color(245, 247, 250));

        JLabel heading = new JLabel("Settings");
        heading.setFont(new Font("Arial", Font.BOLD, 28));
        heading.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel userLabel = new JLabel("Logged-in User ID: " + userId);
        userLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        userLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JButton logoutButton = new JButton("Logout");
        logoutButton.setAlignmentX(Component.LEFT_ALIGNMENT);

        logoutButton.addActionListener(e -> {
            int choice = JOptionPane.showConfirmDialog(
                    this,
                    "Are you sure you want to logout?",
                    "Confirm Logout",
                    JOptionPane.YES_NO_OPTION
            );

            if (choice == JOptionPane.YES_OPTION) {
                dispose();
                new Login();
            }
        });

        panel.add(heading);
        panel.add(Box.createVerticalStrut(25));
        panel.add(userLabel);
        panel.add(Box.createVerticalStrut(30));
        panel.add(logoutButton);

        add(panel);
        setVisible(true);
    }
}

