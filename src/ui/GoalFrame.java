
package ui;

import dao.GoalDAO;
import model.FinancialGoal;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

public class GoalFrame extends JFrame {

    private final int userId;
    private final GoalDAO goalDAO = new GoalDAO();
    private final DefaultTableModel tableModel;

    private JTextField nameField;
    private JTextField amountField;
    private JTextField savedField;
    private JTextField dateField;

    public GoalFrame(int userId) {
        this.userId = userId;

        setTitle("Financial Goals");
        setSize(850, 550);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel mainPanel = new JPanel(new BorderLayout(12, 12));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        mainPanel.setBackground(new Color(15, 23, 42));

        JLabel heading = new JLabel("My Financial Goals");
        heading.setFont(new Font("Arial", Font.BOLD, 25));
        heading.setForeground(Color.WHITE);

        JPanel form = new JPanel(new GridLayout(5, 2, 8, 8));
        form.setBackground(new Color(30, 41, 59));

        nameField = new JTextField();
        amountField = new JTextField();
        savedField = new JTextField("0");
        dateField = new JTextField();

        form.add(new JLabel("Goal Name:"));
        form.add(nameField);
        form.add(new JLabel("Target Amount:"));
        form.add(amountField);
        form.add(new JLabel("Already Saved:"));
        form.add(savedField);
        form.add(new JLabel("Target Date (YYYY-MM-DD):"));
        form.add(dateField);

        JButton addButton = new JButton("Add Goal");
        JButton refreshButton = new JButton("Refresh");

        form.add(addButton);
        form.add(refreshButton);

        tableModel = new DefaultTableModel(
                new String[]{"ID", "Goal", "Target", "Saved", "Remaining", "Target Date", "Progress"}, 0
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        JTable table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);

        addButton.addActionListener(e -> addGoal());
        refreshButton.addActionListener(e -> loadGoals());

        mainPanel.add(heading, BorderLayout.NORTH);
        mainPanel.add(form, BorderLayout.WEST);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        setContentPane(mainPanel);

        loadGoals();
        setVisible(true);
    }

    private void addGoal() {
        try {
            String goalName = nameField.getText().trim();
            double target = Double.parseDouble(amountField.getText().trim());
            double saved = Double.parseDouble(savedField.getText().trim());
            String dateText = dateField.getText().trim();

            if (goalName.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Enter a goal name.");
                return;
            }

            if (target <= 0 || saved < 0 || saved > target) {
                JOptionPane.showMessageDialog(this,
                        "Target must be positive, and saved amount must be between 0 and target.");
                return;
            }

            Date targetDate = null;

            if (!dateText.isEmpty()) {
                targetDate = Date.valueOf(LocalDate.parse(dateText));
            }

            FinancialGoal goal = new FinancialGoal(
                    userId, goalName, target, saved, targetDate
            );

            if (goalDAO.addGoal(goal)) {
                JOptionPane.showMessageDialog(this, "Financial goal added!");
                nameField.setText("");
                amountField.setText("");
                savedField.setText("0");
                dateField.setText("");
                loadGoals();
            } else {
                JOptionPane.showMessageDialog(this, "Unable to add goal.");
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Check the amounts and date. Use YYYY-MM-DD for the date.");
        }
    }

    private void loadGoals() {
        tableModel.setRowCount(0);

        List<FinancialGoal> goals = goalDAO.getGoalsByUser(userId);

        for (FinancialGoal goal : goals) {
            double remaining = goal.getTargetAmount() - goal.getSavedAmount();
            double progress = goal.getSavedAmount() / goal.getTargetAmount() * 100;

            tableModel.addRow(new Object[]{
                    goal.getId(),
                    goal.getGoalName(),
                    goal.getTargetAmount(),
                    goal.getSavedAmount(),
                    remaining,
                    goal.getTargetDate(),
                    String.format("%.1f%%", progress)
            });
        }
    }
}