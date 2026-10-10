
package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import dao.SecurityLogDAO;

public class AdminDashboard extends JFrame {

    private final int adminId;

    private final Color background = new Color(15, 23, 42);
    private final Color sidebarColor = new Color(17, 24, 39);
    private final Color cardColor = new Color(30, 41, 59);
    private final Color green = new Color(34, 197, 94);
    private final Color textColor = new Color(248, 250, 252);
    private final Color mutedColor = new Color(148, 163, 184);

    public AdminDashboard(int adminId) {
        this.adminId = adminId;

        setTitle("Kuber Manager | Admin Dashboard");
        setSize(1100, 680);
        setMinimumSize(new Dimension(900, 580));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(background);

        // LEFT SIDEBAR
        JPanel sidebar = new JPanel();
        sidebar.setBackground(sidebarColor);
        sidebar.setPreferredSize(new Dimension(235, 0));
        sidebar.setBorder(
                BorderFactory.createEmptyBorder(25, 16, 20, 16)
        );
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));

        JLabel logo = new JLabel("KUBER MANAGER");
        logo.setFont(new Font("Arial", Font.BOLD, 20));
        logo.setForeground(green);
        logo.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel adminLabel = new JLabel("ADMINISTRATOR");
        adminLabel.setFont(new Font("Arial", Font.BOLD, 11));
        adminLabel.setForeground(mutedColor);
        adminLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        sidebar.add(logo);
        sidebar.add(Box.createVerticalStrut(8));
        sidebar.add(adminLabel);
        sidebar.add(Box.createVerticalStrut(45));

        JButton dashboardButton = createButton(
                "  Dashboard", cardColor, textColor
        );

        JButton logsButton = createButton(
                "  Security Logs", sidebarColor, textColor
        );
        JButton userSecurityButton = createButton(
                "  User Security", sidebarColor, textColor
        );

        JButton logoutButton = createButton(
                "  Logout", new Color(127, 29, 29), Color.WHITE
        );


        sidebar.add(dashboardButton);
        sidebar.add(Box.createVerticalStrut(12));

        sidebar.add(userSecurityButton);
        sidebar.add(Box.createVerticalStrut(12));

        sidebar.add(logsButton);
        sidebar.add(Box.createVerticalGlue());
        sidebar.add(logoutButton);
        // MAIN CONTENT
        JPanel content = new JPanel(new BorderLayout(0, 25));
        content.setBackground(background);
        content.setBorder(
                BorderFactory.createEmptyBorder(30, 30, 30, 30)
        );

        // HEADER
        JPanel header = new JPanel(new BorderLayout());
        header.setOpaque(false);

        JPanel headingPanel = new JPanel();
        headingPanel.setOpaque(false);
        headingPanel.setLayout(
                new BoxLayout(headingPanel, BoxLayout.Y_AXIS)
        );

        JLabel heading = new JLabel("Admin Dashboard");
        heading.setFont(new Font("Arial", Font.BOLD, 30));
        heading.setForeground(textColor);

        JLabel subtitle = new JLabel(
                "Monitor security and manage administrator access."
        );
        subtitle.setFont(new Font("Arial", Font.PLAIN, 14));
        subtitle.setForeground(mutedColor);

        headingPanel.add(heading);
        headingPanel.add(Box.createVerticalStrut(8));
        headingPanel.add(subtitle);

        JLabel adminBadge = new JLabel("  ADMIN #" + adminId + "  ");
        adminBadge.setFont(new Font("Arial", Font.BOLD, 13));
        adminBadge.setForeground(green);
        adminBadge.setOpaque(true);
        adminBadge.setBackground(cardColor);
        adminBadge.setBorder(
                BorderFactory.createEmptyBorder(12, 10, 12, 10)
        );

        header.add(headingPanel, BorderLayout.WEST);
        header.add(adminBadge, BorderLayout.EAST);

        // DASHBOARD CARDS
        JPanel cardsPanel = new JPanel(new GridLayout(1, 2, 20, 20));
        cardsPanel.setOpaque(false);

        JPanel securityCard = createCard(
                "SECURITY MONITORING",
                "Review security activity",
                "View login attempts, logouts and other recorded events.",
                green
        );

        JPanel accessCard = createCard(
                "USER SECURITY",
                "Control account access",
                "View user status and restrict, block, or reactivate normal user accounts.",
                new Color(96, 165, 250)
        );

        cardsPanel.add(securityCard);
        cardsPanel.add(accessCard);

        // SECURITY LOGS ACTION
        JButton viewLogsButton = createButton(
                "View Security Logs  →", green, Color.BLACK
        );
        viewLogsButton.setPreferredSize(new Dimension(220, 48));
        viewLogsButton.setFont(new Font("Arial", Font.BOLD, 14));

        JPanel actionPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        actionPanel.setOpaque(false);
        actionPanel.add(viewLogsButton);

        JPanel centerPanel = new JPanel(new BorderLayout(0, 20));
        centerPanel.setOpaque(false);
        centerPanel.add(cardsPanel, BorderLayout.CENTER);
        centerPanel.add(actionPanel, BorderLayout.SOUTH);

        // FOOTER
        JLabel footer = new JLabel(
                "Kuber Manager  |  Secure Administration"
        );
        footer.setFont(new Font("Arial", Font.PLAIN, 12));
        footer.setForeground(mutedColor);
        footer.setBorder(
                BorderFactory.createEmptyBorder(15, 0, 0, 0)
        );

        content.add(header, BorderLayout.NORTH);
        content.add(centerPanel, BorderLayout.CENTER);
        content.add(footer, BorderLayout.SOUTH);

        mainPanel.add(sidebar, BorderLayout.WEST);
        mainPanel.add(content, BorderLayout.CENTER);

        setContentPane(mainPanel);

        // BUTTON ACTIONS
        dashboardButton.addActionListener(e -> {
            JOptionPane.showMessageDialog(
                    this,
                    "You are already on the Admin Dashboard."
            );
        });

        logsButton.addActionListener(e -> openSecurityLogs());
        viewLogsButton.addActionListener(e -> openSecurityLogs());
        userSecurityButton.addActionListener(e -> {
            new UserManagement(adminId);
        });

        logoutButton.addActionListener(e -> {
            int choice = JOptionPane.showConfirmDialog(
                    this,
                    "Are you sure you want to logout?",
                    "Confirm Logout",
                    JOptionPane.YES_NO_OPTION
            );

            if (choice == JOptionPane.YES_OPTION) {
                SecurityLogDAO logDAO = new SecurityLogDAO();

                logDAO.addLog(
                        adminId,
                        "LOGOUT",
                        "Administrator logged out"
                );

                dispose();
                new Login();
            }
        });

        setVisible(true);
    }

    private JButton createButton(
            String title, Color bg, Color fg
    ) {
        JButton button = new JButton(title);

        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setForeground(fg);
        button.setBackground(bg);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setOpaque(true);
        button.setCursor(
                Cursor.getPredefinedCursor(Cursor.HAND_CURSOR)
        );
        button.setHorizontalAlignment(SwingConstants.LEFT);
        button.setBorder(
                BorderFactory.createEmptyBorder(14, 16, 14, 12)
        );
        button.setMaximumSize(
                new Dimension(Integer.MAX_VALUE, 48)
        );
        button.setAlignmentX(Component.LEFT_ALIGNMENT);

        Color originalColor = bg;
        Color hoverColor = new Color(
                Math.min(bg.getRed() + 18, 255),
                Math.min(bg.getGreen() + 18, 255),
                Math.min(bg.getBlue() + 18, 255)
        );

        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(hoverColor);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(originalColor);
            }
        });

        return button;
    }

    private JPanel createCard(
            String title,
            String heading,
            String description,
            Color accent
    ) {
        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(cardColor);
        card.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                new Color(51, 65, 85)
                        ),
                        BorderFactory.createEmptyBorder(
                                25, 22, 25, 22
                        )
                )
        );

        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 12));
        titleLabel.setForeground(accent);
        titleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel headingLabel = new JLabel(heading);
        headingLabel.setFont(new Font("Arial", Font.BOLD, 19));
        headingLabel.setForeground(textColor);
        headingLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JTextArea descriptionLabel = new JTextArea(description);
        descriptionLabel.setFont(
                new Font("Arial", Font.PLAIN, 14)
        );
        descriptionLabel.setForeground(mutedColor);
        descriptionLabel.setBackground(cardColor);
        descriptionLabel.setEditable(false);
        descriptionLabel.setFocusable(false);
        descriptionLabel.setLineWrap(true);
        descriptionLabel.setWrapStyleWord(true);
        descriptionLabel.setRows(3);
        descriptionLabel.setBorder(null);
        descriptionLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        card.add(titleLabel);
        card.add(Box.createVerticalStrut(22));
        card.add(headingLabel);
        card.add(Box.createVerticalStrut(12));
        card.add(descriptionLabel);

        return card;
    }

    private void openSecurityLogs() {
        new SecurityLogsFrame();
    }
}