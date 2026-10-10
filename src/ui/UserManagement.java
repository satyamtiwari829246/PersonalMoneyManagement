
package ui;

import database.DBConnection;
import dao.userDAO;
import dao.SecurityLogDAO;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UserManagement extends JFrame {

    private JTable userTable;
    private DefaultTableModel tableModel;
    private final int adminId;

    private final Color background = new Color(15, 23, 42);
    private final Color card = new Color(30, 41, 59);
    private final Color green = new Color(34, 197, 94);
    private final Color white = new Color(248, 250, 252);
    private final Color muted = new Color(148, 163, 184);

    public UserManagement() {
        this(-1);
    }

    public UserManagement(int adminId) {
        this.adminId = adminId;

        setTitle("Kuber Manager | User Security");
        setSize(1050, 620);
        setMinimumSize(new Dimension(850, 500));
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel mainPanel = new JPanel(new BorderLayout(15, 20));
        mainPanel.setBackground(background);
        mainPanel.setBorder(
                BorderFactory.createEmptyBorder(25, 25, 25, 25)
        );

        // HEADER
        JPanel header = new JPanel(new BorderLayout());
        header.setOpaque(false);

        JPanel titlePanel = new JPanel();
        titlePanel.setOpaque(false);
        titlePanel.setLayout(
                new BoxLayout(titlePanel, BoxLayout.Y_AXIS)
        );

        JLabel heading = new JLabel("User Security");
        heading.setFont(new Font("Arial", Font.BOLD, 28));
        heading.setForeground(white);

        JLabel subtitle = new JLabel(
                "View accounts and control user access."
        );
        subtitle.setFont(new Font("Arial", Font.PLAIN, 14));
        subtitle.setForeground(muted);

        titlePanel.add(heading);
        titlePanel.add(Box.createVerticalStrut(7));
        titlePanel.add(subtitle);

        JButton refreshButton = createButton(
                "Refresh", card, white
        );

        header.add(titlePanel, BorderLayout.WEST);
        header.add(refreshButton, BorderLayout.EAST);

        // USER TABLE
        tableModel = new DefaultTableModel(
                new String[]{
                        "ID", "Name", "Email", "Role",
                        "Status", "Created At"
                }, 0
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        userTable = new JTable(tableModel);
        userTable.setFont(new Font("Arial", Font.PLAIN, 13));
        userTable.setRowHeight(32);
        userTable.setSelectionMode(
                ListSelectionModel.SINGLE_SELECTION
        );
        userTable.setBackground(card);
        userTable.setForeground(white);
        userTable.setSelectionBackground(
                new Color(51, 65, 85)
        );
        userTable.setSelectionForeground(white);
        userTable.setGridColor(
                new Color(51, 65, 85)
        );
        userTable.setShowVerticalLines(false);
        userTable.getTableHeader().setFont(
                new Font("Arial", Font.BOLD, 13)
        );
        userTable.getTableHeader().setBackground(
                new Color(51, 65, 85)
        );
        userTable.getTableHeader().setForeground(white);
        userTable.getTableHeader().setReorderingAllowed(false);

        DefaultTableCellRenderer centerRenderer =
                new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(
                SwingConstants.CENTER
        );

        userTable.getColumnModel()
                .getColumn(0).setCellRenderer(centerRenderer);
        userTable.getColumnModel()
                .getColumn(3).setCellRenderer(centerRenderer);
        userTable.getColumnModel()
                .getColumn(4).setCellRenderer(centerRenderer);

        JScrollPane scrollPane = new JScrollPane(userTable);
        scrollPane.getViewport().setBackground(card);
        scrollPane.setBorder(
                BorderFactory.createLineBorder(
                        new Color(51, 65, 85)
                )
        );

        // ACTION BUTTONS
        JButton restrictButton = createButton(
                "Restrict", new Color(180, 83, 9), white
        );

        JButton blockButton = createButton(
                "Block User", new Color(185, 28, 28), white
        );

        JButton activateButton = createButton(
                "Unblock / Activate", green, Color.BLACK
        );

        JPanel actions = new JPanel(
                new FlowLayout(FlowLayout.LEFT, 12, 8)
        );
        actions.setBackground(background);

        JLabel actionLabel = new JLabel("Account actions:");
        actionLabel.setForeground(white);
        actionLabel.setFont(
                new Font("Arial", Font.BOLD, 13)
        );

        actions.add(actionLabel);
        actions.add(restrictButton);
        actions.add(blockButton);
        actions.add(activateButton);

        JLabel note = new JLabel(
                "  Only normal user accounts can be restricted or blocked."
        );
        note.setForeground(muted);
        note.setFont(new Font("Arial", Font.PLAIN, 12));

        JPanel bottomPanel = new JPanel();
        bottomPanel.setOpaque(false);
        bottomPanel.setLayout(
                new BoxLayout(bottomPanel, BoxLayout.Y_AXIS)
        );
        bottomPanel.add(actions);
        bottomPanel.add(note);

        mainPanel.add(header, BorderLayout.NORTH);
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);

        setContentPane(mainPanel);

        // EVENTS
        refreshButton.addActionListener(e -> loadUsers());

        restrictButton.addActionListener(
                e -> changeStatus("RESTRICTED")
        );

        blockButton.addActionListener(
                e -> changeStatus("BLOCKED")
        );

        activateButton.addActionListener(
                e -> changeStatus("ACTIVE")
        );

        loadUsers();
        setVisible(true);
    }

    private JButton createButton(
            String title, Color bg, Color fg
    ) {
        JButton button = new JButton(title);
        button.setFont(new Font("Arial", Font.BOLD, 13));
        button.setBackground(bg);
        button.setForeground(fg);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setOpaque(true);
        button.setCursor(
                Cursor.getPredefinedCursor(
                        Cursor.HAND_CURSOR
                )
        );
        button.setBorder(
                BorderFactory.createEmptyBorder(
                        10, 15, 10, 15
                )
        );
        return button;
    }

    private void loadUsers() {
        tableModel.setRowCount(0);

        String sql = "SELECT id, name, email, role, status, created_at " +
                "FROM users ORDER BY id";

        try (
                Connection con = DBConnection.con();
                PreparedStatement ps = con.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()
        ) {
            while (rs.next()) {
                tableModel.addRow(new Object[]{
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getString("role"),
                        rs.getString("status"),
                        rs.getTimestamp("created_at")
                });
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(
                    this,
                    "Unable to load users: " + e.getMessage(),
                    "Database Error",
                    JOptionPane.ERROR_MESSAGE
            );
            e.printStackTrace();
        }
    }

    private void changeStatus(String newStatus) {
        int selectedRow = userTable.getSelectedRow();

        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(
                    this,
                    "Please select a user first.",
                    "No User Selected",
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        int row = userTable.convertRowIndexToModel(selectedRow);

        int selectedUserId = Integer.parseInt(
                tableModel.getValueAt(row, 0).toString()
        );

        String selectedName =
                tableModel.getValueAt(row, 1).toString();

        String selectedRole =
                tableModel.getValueAt(row, 3).toString();

        String oldStatus =
                tableModel.getValueAt(row, 4).toString();

        // Admin accounts cannot be changed from this screen.
        if (!"User".equalsIgnoreCase(selectedRole)) {
            JOptionPane.showMessageDialog(
                    this,
                    "Security actions are available only for normal user accounts.",
                    "Action Not Allowed",
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        if (oldStatus.equalsIgnoreCase(newStatus)) {
            JOptionPane.showMessageDialog(
                    this,
                    "This user already has status: " + newStatus
            );
            return;
        }

        String action = switch (newStatus) {
            case "RESTRICTED" -> "restrict";
            case "BLOCKED" -> "block";
            default -> "activate / unblock";
        };

        int confirm = JOptionPane.showConfirmDialog(
                this,
                "Are you sure you want to " + action +
                        " this account?\n\nUser: " + selectedName +
                        "\nNew status: " + newStatus,
                "Confirm Security Action",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE
        );

        if (confirm != JOptionPane.YES_OPTION) {
            return;
        }

        userDAO dao = new userDAO();
        boolean updated = dao.updateUserStatus(
                selectedUserId, newStatus
        );

        if (updated) {
            if (adminId > 0) {
                SecurityLogDAO logDAO = new SecurityLogDAO();

                logDAO.addLog(
                        adminId,
                        "USER_" + newStatus,
                        "Admin changed user ID " + selectedUserId +
                                " status from " + oldStatus +
                                " to " + newStatus
                );
            }

            JOptionPane.showMessageDialog(
                    this,
                    "User status updated successfully to " +
                            newStatus + ".",
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE
            );

            loadUsers();

        } else {
            JOptionPane.showMessageDialog(
                    this,
                    "Status update failed. Check the database connection " +
                            "and ensure the selected account is a normal User.",
                    "Update Failed",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }
}