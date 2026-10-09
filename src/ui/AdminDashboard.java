
    package ui;

    import javax.swing.*;
    import java.awt.*;

    import dao.SecurityLogDAO;
    import ui.UserManagement;
    public class AdminDashboard extends JFrame {

        private final int adminId;

        public AdminDashboard(int adminId) {
            this.adminId = adminId;

            setTitle("Personal Money Management - Admin Panel");
            setSize(900, 600);
            setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            setLocationRelativeTo(null);

            Color background = new Color(15, 23, 42);
            Color card = new Color(30, 41, 59);
            Color text = new Color(248, 250, 252);
            Color green = new Color(34, 197, 94);

            JPanel mainPanel = new JPanel(new BorderLayout(15, 15));
            mainPanel.setBackground(background);
            mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

            JLabel heading = new JLabel("Admin Dashboard");
            heading.setForeground(text);
            heading.setFont(new Font("Arial", Font.BOLD, 28));

            JLabel welcome = new JLabel("Administrator ID: " + adminId);
            welcome.setForeground(text);

            JPanel header = new JPanel(new BorderLayout());
            header.setBackground(background);
            header.add(heading, BorderLayout.NORTH);
            header.add(welcome, BorderLayout.SOUTH);

            JPanel menu = new JPanel(new GridLayout(3, 1, 10, 10));
            menu.setBackground(background);

            JButton usersButton = new JButton("User Management");
            JButton logsButton = new JButton("Security Logs");
            JButton logoutButton = new JButton("Logout");

            JButton[] buttons = {
                    usersButton, logsButton, logoutButton
            };

            for (JButton button : buttons) {
                button.setFont(new Font("Arial", Font.BOLD, 16));
                button.setFocusPainted(false);
                button.setBackground(card);
                button.setForeground(text);
            }

            logoutButton.setBackground(green);
            logoutButton.setForeground(Color.BLACK);

            menu.add(usersButton);
            menu.add(logsButton);
            menu.add(logoutButton);

            usersButton.addActionListener(e -> {
                new UserManagement();
            });
            logsButton.addActionListener(e -> {
                new SecurityLogsFrame();
            });


            logoutButton.addActionListener(e -> {
                SecurityLogDAO logDAO = new SecurityLogDAO();

                logDAO.addLog(
                        adminId,
                        "LOGOUT",
                        "Administrator logged out"
                );

                dispose();
                new Login();
            });

            mainPanel.add(header, BorderLayout.NORTH);
            mainPanel.add(menu, BorderLayout.CENTER);

            setContentPane(mainPanel);
            setVisible(true);
        }
    }