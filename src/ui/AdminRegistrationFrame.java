
package ui;

import dao.SecurityLogDAO;
import dao.userDAO;

import javax.swing.*;
import java.awt.*;

public class AdminRegistrationFrame extends JFrame {

    private JTextField nameField;
    private JTextField emailField;
    private JPasswordField passwordField;
    private JPasswordField codeField;

    public AdminRegistrationFrame() {

        setTitle("Admin Registration");
        setSize(450, 450);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel(new GridLayout(0, 1, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(25, 35, 25, 35));

        JLabel title = new JLabel("Admin Registration", JLabel.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 22));

        nameField = new JTextField();
        emailField = new JTextField();
        passwordField = new JPasswordField();
        codeField = new JPasswordField();

        panel.add(title);
        panel.add(new JLabel("Full Name"));
        panel.add(nameField);
        panel.add(new JLabel("Email"));
        panel.add(emailField);
        panel.add(new JLabel("Password"));
        panel.add(passwordField);
        panel.add(new JLabel("Secret Verification Code"));
        panel.add(codeField);

        JButton registerButton = new JButton("Register Admin");
        panel.add(registerButton);

        registerButton.addActionListener(e -> registerAdmin());

        add(panel);
        setVisible(true);
    }

    private void registerAdmin() {

        String name = nameField.getText().trim();
        String email = emailField.getText().trim();
        String password = new String(passwordField.getPassword());
        String code = new String(codeField.getPassword());

        if (name.isEmpty() || email.isEmpty()
                || password.isEmpty() || code.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this, "Please fill in all fields.");
            return;
        }

        userDAO dao = new userDAO();

        boolean success = dao.registerAdmin(
                name, email, password, code);

        SecurityLogDAO logDAO = new SecurityLogDAO();

        logDAO.addLog(
                null,
                "ADMIN_REGISTERED",
                "A new administrator account was registered: " + email
        );
        if (success) {
            JOptionPane.showMessageDialog(
                    this, "Admin registered successfully!");

            dispose();
        } else {
            JOptionPane.showMessageDialog(
                    this,
                    "Registration failed. Check the code or email.",
                    "Registration Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
    public static void main(String[] args){
        new AdminRegistrationFrame();
    }
}
