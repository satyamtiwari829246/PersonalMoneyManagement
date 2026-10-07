        package ui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import model.User;
import dao.userDAO;

public class SignupFrame extends JFrame {
    private JTextField nameField;
    private JTextField emailField;
    private JPasswordField passwordField;
    private JPasswordField confirmPasswordField;

    private final Color BACKGROUND = new Color(15, 23, 42);
    private final Color CARD = new Color(30, 41, 59);
    private final Color INPUT = new Color(51, 65, 85);
    private final Color GREEN = new Color(34, 197, 94);
    private final Color TEXT = new Color(248, 250, 252);
    private final Color MUTED = new Color(148, 163, 184);

    public SignupFrame() {

        setTitle("KuberManneger - Create Account");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        // Main background
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(BACKGROUND);

        // =========================
        // LEFT SIDE - BRANDING
        // =========================

        JPanel leftPanel = new JPanel();
        leftPanel.setPreferredSize(new Dimension(400, 600));
        leftPanel.setBackground(GREEN);
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        leftPanel.setBorder(
                new EmptyBorder(70, 50, 50, 50)
        );

        JLabel logo = new JLabel("KuberMannager");
        logo.setFont(
                new Font("Arial", Font.BOLD, 32)
        );
        logo.setForeground(Color.WHITE);

        JLabel tagline = new JLabel(
                "<html><div style='text-align:center;'>"
                        + "Manage your money.<br>"
                        + "Build your future."
                        + "</div></html>"
        );

        tagline.setFont(
                new Font("Arial", Font.BOLD, 25)
        );
        tagline.setForeground(Color.WHITE);
        tagline.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel description = new JLabel(
                "<html>"
                        + "<div style='width:280px;'>"
                        + "Track your income, control your expenses "
                        + "and achieve your financial goals with ease."
                        + "</div>"
                        + "</html>"
        );

        description.setFont(
                new Font("Arial", Font.PLAIN, 16)
        );
        description.setForeground(
                new Color(220, 252, 231)
        );

        description.setAlignmentX(Component.LEFT_ALIGNMENT);

        leftPanel.add(logo);
        leftPanel.add(Box.createVerticalStrut(100));
        leftPanel.add(tagline);
        leftPanel.add(Box.createVerticalStrut(20));
        leftPanel.add(description);

        // =========================
        // RIGHT SIDE
        // =========================

        JPanel rightPanel = new JPanel(new GridBagLayout());
        rightPanel.setBackground(BACKGROUND);

        RoundedPanel card = new RoundedPanel(
                CARD,
                30
        );

        card.setPreferredSize(
                new Dimension(400, 520)
        );

        card.setLayout(
                new BoxLayout(card, BoxLayout.Y_AXIS)
        );

        card.setBorder(
                new EmptyBorder(35, 45, 35, 45)
        );

        // Heading
        JLabel title = new JLabel("Create Account");

        title.setFont(
                new Font("Arial", Font.BOLD, 28)
        );
        title.setForeground(TEXT);
        title.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel subtitle = new JLabel(
                "Start managing your money today"
        );

        subtitle.setFont(
                new Font("Arial", Font.PLAIN, 14)
        );
        subtitle.setForeground(MUTED);
        subtitle.setAlignmentX(Component.LEFT_ALIGNMENT);

        card.add(title);
        card.add(Box.createVerticalStrut(8));
        card.add(subtitle);

        card.add(Box.createVerticalStrut(25));

        // =========================
        // NAME
        // =========================

        JLabel nameLabel = createLabel("Full Name");

        nameField = createTextField(
                "Enter your full name"
        );

        card.add(nameLabel);
        card.add(Box.createVerticalStrut(7));
        card.add(nameField);

        card.add(Box.createVerticalStrut(15));

        // =========================
        // EMAIL
        // =========================

        JLabel emailLabel = createLabel("Email Address");

        emailField = createTextField(
                "Enter your email"
        );

        card.add(emailLabel);
        card.add(Box.createVerticalStrut(7));
        card.add(emailField);

        card.add(Box.createVerticalStrut(15));

        // =========================
        // PASSWORD
        // =========================

        JLabel passwordLabel = createLabel("Password");

        passwordField = new JPasswordField();

        stylePasswordField(
                passwordField,
                "Create a password"
        );

        card.add(passwordLabel);
        card.add(Box.createVerticalStrut(7));
        card.add(passwordField);

        card.add(Box.createVerticalStrut(15));

        // =========================
        // CONFIRM PASSWORD
        // =========================

        JLabel confirmLabel =
                createLabel("Confirm Password");

        confirmPasswordField =
                new JPasswordField();

        stylePasswordField(
                confirmPasswordField,
                "Confirm your password"
        );

        card.add(confirmLabel);
        card.add(Box.createVerticalStrut(7));
        card.add(confirmPasswordField);

        card.add(Box.createVerticalStrut(25));

        // =========================
        // SIGNUP BUTTON
        // =========================

        ModernButton signupButton =
                new ModernButton(
                        "Create Account",
                        GREEN
                );

        signupButton.setMaximumSize(
                new Dimension(
                        Integer.MAX_VALUE,
                        48
                )
        );

        signupButton.addActionListener(
                e -> signup()
        );

        card.add(signupButton);

        card.add(Box.createVerticalStrut(18));

        // =========================
        // LOGIN TEXT
        // =========================

        JPanel loginPanel =
                new JPanel();

        loginPanel.setOpaque(false);

        JLabel already =
                new JLabel("Already have an account? ");

        already.setForeground(MUTED);

        JLabel login =
                new JLabel("Login");

        login.setForeground(GREEN);
        login.setFont(
                new Font("Arial", Font.BOLD, 13)
        );

        loginPanel.add(already);
        loginPanel.add(login);

        card.add(loginPanel);

        rightPanel.add(card);

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

    // ==========================================
    // CREATE LABEL
    // ==========================================

    private JLabel createLabel(String text) {

        JLabel label = new JLabel(text);

        label.setFont(
                new Font("Arial", Font.BOLD, 13)
        );

        label.setForeground(TEXT);

        label.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

        return label;
    }

    // ==========================================
    // TEXT FIELD
    // ==========================================

    private JTextField createTextField(
            String placeholder
    ) {

        JTextField field =
                new JTextField();

        field.setFont(
                new Font("Arial", Font.PLAIN, 14)
        );

        field.setForeground(TEXT);

        field.setBackground(INPUT);

        field.setCaretColor(TEXT);

        field.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                new Color(71, 85, 105)
                        ),
                        new EmptyBorder(
                                0, 15, 0, 15
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

    // ==========================================
    // PASSWORD FIELD
    // ==========================================

    private void stylePasswordField(
            JPasswordField field,
            String placeholder
    ) {

        field.setFont(
                new Font("Arial", Font.PLAIN, 14)
        );

        field.setForeground(TEXT);

        field.setBackground(INPUT);

        field.setCaretColor(TEXT);

        field.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                new Color(71, 85, 105)
                        ),
                        new EmptyBorder(
                                0, 15, 0, 15
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

    // ==========================================
    // SIGNUP FUNCTION
    // ==========================================

    private void signup() {

        String name =
                nameField.getText().trim();

        String email =
                emailField.getText().trim();

        String password =
                new String(
                        passwordField.getPassword()
                );

        String confirmPassword =
                new String(
                        confirmPasswordField
                                .getPassword()
                );

        // Empty check
        if (name.isEmpty()
                || email.isEmpty()
                || password.isEmpty()
                || confirmPassword.isEmpty()) {

            showMessage(
                    "Please fill all fields."
            );


            return;
        }

        // Password check
        if (!password.equals(confirmPassword)) {

            showMessage(
                    "Passwords do not match."
            );

            return;
        }

        // Success
        showMessage(
                "Account created successfully!"
        );

        User u = new User(name,email,password);//pusing the value to user.java
        userDAO d = new userDAO();
        boolean result = userDAO.createUser(u);
        System.out.println(
                "Name: " + name
        );

        System.out.println(
                "Email: " + email
        );
    }

    // ==========================================
    // MESSAGE
    // ==========================================

    private void showMessage(
            String message
    ) {

        JOptionPane.showMessageDialog(
                this,
                message,
                "MoneyMate",
                JOptionPane.INFORMATION_MESSAGE
        );
    }

    // ==========================================
    // MAIN
    // ==========================================

    public static void main(String[] args) {

        SwingUtilities.invokeLater(
                SignupFrame::new
        );
    }

    // ==========================================
    // ROUNDED PANEL
    // ==========================================

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

    // ==========================================
    // MODERN BUTTON
    // ==========================================

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

            setForeground(Color.WHITE);

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

            Color buttonColor = color;

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
}

