package ui;

import dao.BudgetDAO;

import javax.swing.*;
import java.awt.*;
import java.sql.ResultSet;

public class BudgetListFrame extends JFrame {

    private int userId;
    private JPanel budgetPanel;


    public BudgetListFrame(int userId) {

        this.userId = userId;

        setTitle("My Budgets");
        setSize(600, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        budgetPanel = new JPanel();
        budgetPanel.setLayout(new BoxLayout(budgetPanel, BoxLayout.Y_AXIS));

        JScrollPane scrollPane =
                new JScrollPane(budgetPanel);

        add(scrollPane);

        loadBudgets();

        setVisible(true);
    }

    private void loadBudgets() {

        BudgetDAO budgetDAO = new BudgetDAO();

        try {

            ResultSet rs =
                    budgetDAO.getBudgetsByUserId(userId);

            boolean found = false;

            while (rs != null && rs.next()) {

                found = true;

                int id = rs.getInt("id");
                String category = rs.getString("category");
                double amount = rs.getDouble("amount");
                String duration = rs.getString("duration");

                double spent = budgetDAO.getSpentAmount(userId, category);
                double remaining = amount - spent;

                if (remaining < 0) {
                    remaining = 0;
                }

                JPanel card = new JPanel(
                        new GridLayout(4, 2, 10, 10)
                );

                card.setBorder(
                        BorderFactory.createTitledBorder(
                                "Budget"
                        )
                );

                card.setMaximumSize(
                        new Dimension(550, 100)
                );

                card.add(
                        new JLabel("Category: " + category)
                );

                card.add(
                        new JLabel("Amount: ₹" + amount)
                );

                card.add(
                        new JLabel("Duration: " + duration)
                );
                card.add(
                        new JLabel("Spent: ₹" + spent)
                );

                card.add(
                        new JLabel("Remaining: ₹" + remaining)
                );
                double progress = 0;

                if (amount > 0) {
                    progress = (spent / amount) * 100;
                }

                int progressValue = (int) Math.min(progress, 100);

                JProgressBar progressBar =
                        new JProgressBar(0, 100);

                progressBar.setValue(progressValue);
                progressBar.setStringPainted(true);
                progressBar.setString(progressValue + "%");

                card.add(progressBar);
                JButton deleteButton =
                        new JButton("Delete");

                card.add(deleteButton);

                deleteButton.addActionListener(e -> {

                    int confirm =
                            JOptionPane.showConfirmDialog(
                                    this,
                                    "Delete this budget?",
                                    "Confirm",
                                    JOptionPane.YES_NO_OPTION
                            );

                    if (confirm ==
                            JOptionPane.YES_OPTION) {

                        boolean deleted =
                                budgetDAO.deleteBudget(
                                        id,
                                        userId
                                );

                        if (deleted) {

                            JOptionPane.showMessageDialog(
                                    this,
                                    "Budget deleted!"
                            );

                            dispose();
                            new BudgetListFrame(userId);

                        } else {

                            JOptionPane.showMessageDialog(
                                    this,
                                    "Failed to delete budget."
                            );
                        }
                    }
                });

                budgetPanel.add(card);

                budgetPanel.add(
                        Box.createVerticalStrut(10)
                );
            }

            if (!found) {

                budgetPanel.add(
                        new JLabel("No budgets found.")
                );
            }

        } catch (Exception e) {

            e.printStackTrace();

            JOptionPane.showMessageDialog(
                    this,
                    "Error loading budgets."
            );
        }
    }
}