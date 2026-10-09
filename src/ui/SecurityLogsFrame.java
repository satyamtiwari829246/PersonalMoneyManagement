
package ui;

import dao.SecurityLogDAO;
import database.DBConnection;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class SecurityLogsFrame extends JFrame {

    private final DefaultTableModel tableModel;

    public SecurityLogsFrame() {
        setTitle("Security Logs");
        setSize(900, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        Color background = new Color(15, 23, 42);

        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBackground(background);
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JLabel heading = new JLabel("Security Logs");
        heading.setFont(new Font("Arial", Font.BOLD, 24));
        heading.setForeground(Color.WHITE);

        tableModel = new DefaultTableModel(
                new String[]{"ID", "User ID", "Action", "Details", "Created At"}, 0
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        JTable table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);

        JButton refreshButton = new JButton("Refresh");
        refreshButton.addActionListener(e -> loadLogs());

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setOpaque(false);
        topPanel.add(heading, BorderLayout.WEST);
        topPanel.add(refreshButton, BorderLayout.EAST);

        panel.add(topPanel, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);

        setContentPane(panel);
        loadLogs();
        setVisible(true);
    }

    private void loadLogs() {
        tableModel.setRowCount(0);

        String sql = "SELECT id, user_id, action, details, created_at " +
                "FROM security_logs ORDER BY created_at DESC";

        try (Connection con = DBConnection.con();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                tableModel.addRow(new Object[]{
                        rs.getInt("id"),
                        rs.getObject("user_id"),
                        rs.getString("action"),
                        rs.getString("details"),
                        rs.getTimestamp("created_at")
                });
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(
                    this,
                    "Unable to load security logs: " + e.getMessage(),
                    "Database Error",
                    JOptionPane.ERROR_MESSAGE
            );
            e.printStackTrace();
        }
    }
}