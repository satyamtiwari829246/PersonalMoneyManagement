package ui;

import dao.BudgetDAO;
import model.Budget;

import javax.swing.*;
import java.awt.*;

public class BudgetFrame extends JFrame {

    private int userId;

    private JTextField categoryField;
    private JTextField amountField;
    private JComboBox<String> durationBox;

    public BudgetFrame(int userId) {

        this.userId = userId;

        setTitle("Budget Management");
        setSize(500, 450);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(5, 2, 15, 15));
        panel.setBorder(
                BorderFactory.createEmptyBorder(
                        30, 30, 30, 30
                )
        );

        JLabel title = new JLabel("Create Budget");
        title.setFont(new Font("Arial", Font.BOLD, 24));

        JLabel categoryLabel =
                new JLabel("Category:");

        categoryField =
                new JTextField();

        JLabel amountLabel =
                new JLabel("Amount:");

        amountField =
                new JTextField();

        JLabel durationLabel =
                new JLabel("Duration:");

        durationBox =
                new JComboBox<>(
                        new String[]{
                                "Weekly",
                                "Monthly",
                                "Yearly"
                        }
                );

        JButton addButton =
                new JButton("Add Budget");

        JButton cancelButton =
                new JButton("Cancel");

        panel.add(title);
        panel.add(new JLabel(""));

        panel.add(categoryLabel);
        panel.add(categoryField);

        panel.add(amountLabel);
        panel.add(amountField);

        panel.add(durationLabel);
        panel.add(durationBox);

        panel.add(addButton);
        panel.add(cancelButton);

        add(panel);

        // Add Budget
        addButton.addActionListener(e -> addBudget());

        // Close window
        cancelButton.addActionListener(e -> dispose());

        setVisible(true);
    }

    private void addBudget() {

        String category =
                categoryField.getText().trim();

        String amountText =
                amountField.getText().trim();

        String duration =
                durationBox.getSelectedItem().toString();

        if (category.isEmpty() ||
                amountText.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please fill all fields."
            );

            return;
        }

        try {

            double amount =
                    Double.parseDouble(amountText);

            if (amount <= 0) {

                JOptionPane.showMessageDialog(
                        this,
                        "Amount must be greater than 0."
                );

                return;
            }

            Budget budget =
                    new Budget(
                            userId,
                            category,
                            amount,
                            duration
                    );

            BudgetDAO budgetDAO =
                    new BudgetDAO();

            boolean success =
                    budgetDAO.addBudget(budget);

            if (success) {

                JOptionPane.showMessageDialog(
                        this,
                        "Budget added successfully!"
                );

                categoryField.setText("");
                amountField.setText("");

            } else {

                JOptionPane.showMessageDialog(
                        this,
                        "Failed to add budget."
                );
            }

        } catch (NumberFormatException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please enter a valid amount."
            );
        }
    }
}