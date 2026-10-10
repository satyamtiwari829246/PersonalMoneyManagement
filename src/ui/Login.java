
        package ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagLayout;
import java.awt.RenderingHints;
import java.awt.geom.RoundRectangle2D;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;

import dao.SecurityLogDAO;
import dao.userDAO;
public class Login extends JFrame {

    private JTextField emailField;
    private JPasswordField passwordField;

    // Colors
    private final Color BACKGROUND = new Color(15, 23, 42);
    private final Color CARD = new Color(30, 41, 59);
    private final Color INPUT = new Color(51, 65, 85);
    private final Color GREEN = new Color(34, 197, 94);
    private final Color TEXT = new Color(248, 250, 252);
    private final Color MUTED = new Color(148, 163, 184);

    public Login() {

        setTitle("KuberManager - Login");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);


        // MAIN PANEL


        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(BACKGROUND);


        // LEFT SIDE


        JPanel leftPanel = new JPanel();

        leftPanel.setPreferredSize(
                new Dimension(400, 600)
        );

        leftPanel.setBackground(GREEN);

        leftPanel.setLayout(
                new BoxLayout(
                        leftPanel,
                        BoxLayout.Y_AXIS
                )
        );

        leftPanel.setBorder(
                new EmptyBorder(
                        70,
                        50,
                        50,
                        10
                )
        );

        // Logo
        JLabel logo = new JLabel("KuberManager");

        logo.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        32
                )
        );

        logo.setForeground(Color.WHITE);

        // Main text
        JLabel heading = new JLabel(
                "<html>"
                        + "Take control of<br>"
                        + "your money."
                        + "</html>"
        );

        heading.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        30
                )
        );

        heading.setForeground(Color.WHITE);

        heading.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

        // Description
        JLabel description = new JLabel(
                "<html>"
                        + "<div style='width:280px;'>"
                        + "Track your income, manage expenses "
                        + "and reach your financial goals."
                        + "</div>"
                        + "</html>"
        );

        description.setFont(
                new Font(
                        "Arial",
                        Font.PLAIN,
                        16
                )
        );

        description.setForeground(
                new Color(220, 252, 231)
        );

        description.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

        leftPanel.add(logo);

        leftPanel.add(
                Box.createVerticalStrut(110)
        );

        leftPanel.add(heading);

        leftPanel.add(
                Box.createVerticalStrut(20)
        );

        leftPanel.add(description);


        // RIGHT SIDE


        JPanel rightPanel =
                new JPanel(
                        new GridBagLayout()
                );

        rightPanel.setBackground(
                BACKGROUND
        );

        // Login card
        RoundedPanel card =
                new RoundedPanel(
                        CARD,
                        30
                );

        card.setPreferredSize(
                new Dimension(
                        400,
                        430
                )
        );

        card.setLayout(
                new BoxLayout(
                        card,
                        BoxLayout.Y_AXIS
                )
        );

        card.setBorder(
                new EmptyBorder(
                        40,
                        45,
                        40,
                        45
                )
        );


        // TITLE


        JLabel title =
                new JLabel(
                        "Welcome Back"
                );

        title.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        28
                )
        );

        title.setForeground(TEXT);

        title.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

        // Subtitle
        JLabel subtitle =
                new JLabel(
                        "Login to continue to your account"
                );

        subtitle.setFont(
                new Font(
                        "Arial",
                        Font.PLAIN,
                        14
                )
        );

        subtitle.setForeground(MUTED);

        subtitle.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

        card.add(title);

        card.add(
                Box.createVerticalStrut(8)
        );

        card.add(subtitle);

        card.add(
                Box.createVerticalStrut(35)
        );


        // EMAIL


        JLabel emailLabel =
                createLabel(
                        "Email Address"
                );

        emailField =
                createTextField();

        card.add(emailLabel);

        card.add(
                Box.createVerticalStrut(8)
        );

        card.add(emailField);

        card.add(
                Box.createVerticalStrut(20)
        );


        // PASSWORD


        JLabel passwordLabel =
                createLabel(
                        "Password"
                );

        passwordField =
                new JPasswordField();

        stylePasswordField(
                passwordField
        );

        card.add(passwordLabel);

        card.add(
                Box.createVerticalStrut(8)
        );

        card.add(passwordField);

        // forgot passward
        JLabel forgotPassword =
                new JLabel(
                        "Forgot Password?"
                );

        forgotPassword.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        12
                )
        );

        forgotPassword.setForeground(
                GREEN
        );

        forgotPassword.setCursor(
                new Cursor(
                        Cursor.HAND_CURSOR
                )
        );

        forgotPassword.setAlignmentX(
                Component.RIGHT_ALIGNMENT
        );

        card.add(
                Box.createVerticalStrut(10)
        );

        card.add(forgotPassword);

        card.add(
                Box.createVerticalStrut(25)
        );


        // LOGIN BUTTON

        ModernButton loginButton =
                new ModernButton(
                        "Login",
                        GREEN
                );

        loginButton.setMaximumSize(
                new Dimension(
                        Integer.MAX_VALUE,
                        48
                )
        );

        loginButton.addActionListener(
                e -> login()
        );

        card.add(loginButton);

        card.add(
                Box.createVerticalStrut(25)
        );


        // SIGNUP LINK


        JPanel signupPanel =
                new JPanel();

        signupPanel.setOpaque(false);

        JLabel text =
                new JLabel(
                        "Don't have an account? "
                );

        text.setForeground(
                MUTED
        );

        JLabel signup =
                new JLabel(
                        "Sign Up"
                );

        signup.setForeground(
                GREEN
        );

        signup.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        13
                )
        );

        signup.setCursor(
                new Cursor(
                        Cursor.HAND_CURSOR
                )
        );

        signup.addMouseListener(
                new java.awt.event.MouseAdapter() {

                    @Override
                    public void mouseClicked(
                            java.awt.event.MouseEvent e
                    ) {

                        new SignupFrame();

                        dispose();
                    }
                }
        );

        signupPanel.add(text);
        signupPanel.add(signup);

        card.add(signupPanel);

        rightPanel.add(card);


        // ADD PANELS


        mainPanel.add(
                leftPanel,
                BorderLayout.WEST
        );

        mainPanel.add(
                rightPanel,
                BorderLayout.CENTER
        );

        add(mainPanel);

        setVisible(true);
    }


    // CREATE LABEL


    private JLabel createLabel(
            String text
    ) {

        JLabel label =
                new JLabel(text);

        label.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        13
                )
        );

        label.setForeground(
                TEXT
        );

        label.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

        return label;
    }


    // TEXT FIELD


    private JTextField createTextField() {

        JTextField field =
                new JTextField();

        field.setFont(
                new Font(
                        "Arial",
                        Font.PLAIN,
                        14
                )
        );

        field.setForeground(
                TEXT
        );

        field.setBackground(
                INPUT
        );

        field.setCaretColor(
                TEXT
        );

        field.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                new Color(
                                        71,
                                        85,
                                        105
                                )
                        ),
                        new EmptyBorder(
                                0,
                                15,
                                0,
                                15
                        )
                )
        );

        field.setMaximumSize(
                new Dimension(
                        Integer.MAX_VALUE,
                        43
                )
        );

        return field;
    }


    // PASSWORD FIELD


    private void stylePasswordField(
            JPasswordField field
    ) {

        field.setFont(
                new Font(
                        "Arial",
                        Font.PLAIN,
                        14
                )
        );

        field.setForeground(
                TEXT
        );

        field.setBackground(
                INPUT
        );

        field.setCaretColor(
                TEXT
        );

        field.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                new Color(
                                        71,
                                        85,
                                        105
                                )
                        ),
                        new EmptyBorder(
                                0,
                                15,
                                0,
                                15
                        )
                )
        );

        field.setMaximumSize(
                new Dimension(
                        Integer.MAX_VALUE,
                        43
                )
        );
    }


    // LOGIN FUNCTION



    private void login() {

        String email = emailField.getText().trim();
        String password = new String(passwordField.getPassword());

        if (email.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(
                    this,
                    "Please enter email and password.",
                    "KuberManager",
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        userDAO u1 = new userDAO();

        boolean result = u1.loginuser(email, password);

        if (!result) {
            SecurityLogDAO logDAO = new SecurityLogDAO();
            logDAO.addLog(
                    null,
                    "LOGIN_FAILED",
                    "Failed login attempt for email: " + email
            );

            JOptionPane.showMessageDialog(
                    this,
                    "Invalid email or password!",
                    "KuberManager",
                    JOptionPane.ERROR_MESSAGE
            );
            return;
        }

        int userId = u1.getUserId(email, password);
        String role = u1.getUserRole(email, password);

        if (userId == -1 || role == null) {
            JOptionPane.showMessageDialog(
                    this,
                    "Unable to retrieve account details.",
                    "Login Error",
                    JOptionPane.ERROR_MESSAGE
            );
            return;
        }
        //login log added here
        SecurityLogDAO logDAO = new SecurityLogDAO();
        logDAO.addLog(
                userId,
                "LOGIN_SUCCESS",
                "User logged in successfully"
        );


        if ("Admin".equalsIgnoreCase(role)) {
            new AdminDashboard(userId);
        } else if ("User".equalsIgnoreCase(role)) {
            new Dashboard(userId);
        } else {
            JOptionPane.showMessageDialog(
                    this,
                    "Invalid account role.",
                    "Login Error",
                    JOptionPane.ERROR_MESSAGE
            );
            return;
        }

        JOptionPane.showMessageDialog(
                this,
                "Login Successful!",
                "KuberManager",
                JOptionPane.INFORMATION_MESSAGE
        );

        dispose();
    }


    // ROUNDED PANEL


    static class RoundedPanel
            extends JPanel {

        private final Color color;
        private final int radius;

        RoundedPanel(
                Color color,
                int radius
        ) {

            this.color = color;
            this.radius = radius;

            setOpaque(false);
        }

        @Override
        protected void paintComponent(
                Graphics g
        ) {

            Graphics2D g2 =
                    (Graphics2D) g.create();

            g2.setRenderingHint(
                    RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON
            );

            g2.setColor(color);

            g2.fillRoundRect(
                    0,
                    0,
                    getWidth(),
                    getHeight(),
                    radius,
                    radius
            );

            g2.dispose();

            super.paintComponent(g);
        }
    }


    // MODERN BUTTON


    static class ModernButton
            extends JButton {

        private final Color color;

        ModernButton(
                String text,
                Color color
        ) {

            super(text);

            this.color = color;

            setFont(
                    new Font(
                            "Arial",
                            Font.BOLD,
                            14
                    )
            );

            setForeground(
                    Color.WHITE
            );

            setFocusPainted(false);
            setBorderPainted(false);
            setContentAreaFilled(false);

            setCursor(
                    new Cursor(
                            Cursor.HAND_CURSOR
                    )
            );
        }

        @Override
        protected void paintComponent(
                Graphics g
        ) {

            Graphics2D g2 =
                    (Graphics2D) g.create();

            g2.setRenderingHint(
                    RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON
            );

            Color buttonColor =
                    color;

            if (getModel().isPressed()) {

                buttonColor =
                        new Color(
                                22,
                                163,
                                74
                        );

            } else if (
                    getModel().isRollover()
            ) {

                buttonColor =
                        new Color(
                                74,
                                222,
                                128
                        );
            }

            g2.setColor(buttonColor);

            g2.fill(
                    new RoundRectangle2D.Float(
                            0,
                            0,
                            getWidth(),
                            getHeight(),
                            16,
                            16
                    )
            );

            g2.dispose();

            super.paintComponent(g);
        }
    }


    // MAIN


    public static void main(
            String[] args
    ) {

        SwingUtilities.invokeLater(
                Login::new
        );
    }
}
