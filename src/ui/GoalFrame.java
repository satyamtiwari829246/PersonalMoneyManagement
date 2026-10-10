
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

    private final Color background = new Color(15, 23, 42);
    private final Color cardColor = new Color(30, 41, 59);
    private final Color green = new Color(34, 197, 94);
    private final Color textColor = new Color(248, 250, 252);
    private final Color mutedColor = new Color(148, 163, 184);

    public GoalFrame(int userId) {
        this.userId = userId;

        setTitle("Kuber Manager | Financial Goals");
        setSize(1000, 650);
        setMinimumSize(new Dimension(850, 550));
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel mainPanel = new JPanel(new BorderLayout(15, 18));
        mainPanel.setBackground(background);
        mainPanel.setBorder(
                BorderFactory.createEmptyBorder(22, 22, 22, 22)
        );

        // HEADER
        JPanel header = new JPanel();
        header.setOpaque(false);
        header.setLayout(
                new BoxLayout(header, BoxLayout.Y_AXIS)
        );

        JLabel heading = new JLabel("My Financial Goals");
        heading.setFont(new Font("Arial", Font.BOLD, 28));
        heading.setForeground(textColor);

        JLabel subtitle = new JLabel(
                "Plan your savings and track your progress."
        );
        subtitle.setFont(new Font("Arial", Font.PLAIN, 14));
        subtitle.setForeground(mutedColor);

        header.add(heading);
        header.add(Box.createVerticalStrut(6));
        header.add(subtitle);

        // INPUT FORM
        JPanel form = new JPanel(new GridBagLayout());
        form.setBackground(cardColor);
        form.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                new Color(51, 65, 85)
                        ),
                        BorderFactory.createEmptyBorder(
                                15, 15, 15, 15
                        )
                )
        );

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(7, 8, 7, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;

        nameField = new JTextField();
        amountField = new JTextField();
        savedField = new JTextField("0");
        dateField = new JTextField();

        addFormRow(form, gbc, 0, "Goal Name:", nameField);
        addFormRow(form, gbc, 1, "Target Amount:", amountField);
        addFormRow(form, gbc, 2, "Already Saved:", savedField);
        addFormRow(form, gbc, 3, "Target Date (YYYY-MM-DD):", dateField);

        JButton addButton = new JButton("Add Goal");
        addButton.setFont(new Font("Arial", Font.BOLD, 14));
        addButton.setBackground(green);
        addButton.setForeground(Color.BLACK);
        addButton.setFocusPainted(false);
        addButton.setCursor(
                Cursor.getPredefinedCursor(Cursor.HAND_CURSOR)
        );

        JButton refreshButton = new JButton("Refresh");
        refreshButton.setFont(new Font("Arial", Font.BOLD, 14));
        refreshButton.setBackground(new Color(51, 65, 85));
        refreshButton.setForeground(textColor);
        refreshButton.setFocusPainted(false);
        refreshButton.setCursor(
                Cursor.getPredefinedCursor(Cursor.HAND_CURSOR)
        );

        JPanel buttonPanel = new JPanel(
                new FlowLayout(FlowLayout.LEFT, 10, 0)
        );
        buttonPanel.setOpaque(false);
        buttonPanel.add(addButton);
        buttonPanel.add(refreshButton);

        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        gbc.weightx = 1;
        form.add(buttonPanel, gbc);

        // GOALS TABLE
        tableModel = new DefaultTableModel(
                new String[]{
                        "ID", "Goal", "Target", "Saved",
                        "Remaining", "Target Date", "Progress"
                }, 0
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        JTable table = new JTable(tableModel);
        table.setRowHeight(30);
        table.setFont(new Font("Arial", Font.PLAIN, 13));
        table.setBackground(cardColor);
        table.setForeground(textColor);
        table.setGridColor(new Color(51, 65, 85));
        table.setSelectionBackground(new Color(51, 65, 85));
        table.setSelectionForeground(textColor);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

        table.getTableHeader().setFont(
                new Font("Arial", Font.BOLD, 12)
        );
        table.getTableHeader().setBackground(
                new Color(51, 65, 85)
        );
        table.getTableHeader().setForeground(textColor);
        table.getTableHeader().setReorderingAllowed(false);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.getViewport().setBackground(cardColor);
        scrollPane.setBorder(
                BorderFactory.createLineBorder(
                        new Color(51, 65, 85)
                )
        );

        JPanel tablePanel = new JPanel(new BorderLayout(0, 10));
        tablePanel.setOpaque(false);

        JLabel tableHeading = new JLabel("Your Goals");
        tableHeading.setFont(new Font("Arial", Font.BOLD, 18));
        tableHeading.setForeground(textColor);

        tablePanel.add(tableHeading, BorderLayout.NORTH);
        tablePanel.add(scrollPane, BorderLayout.CENTER);

        // MAIN LAYOUT: HEADER, FORM, TABLE
        JPanel topPanel = new JPanel(new BorderLayout(0, 15));
        topPanel.setOpaque(false);
        topPanel.add(header, BorderLayout.NORTH);
        topPanel.add(form, BorderLayout.CENTER);

        mainPanel.add(topPanel, BorderLayout.NORTH);
        mainPanel.add(tablePanel, BorderLayout.CENTER);

        addButton.addActionListener(e -> addGoal());
        refreshButton.addActionListener(e -> loadGoals());

        setContentPane(mainPanel);

        loadGoals();
        setVisible(true);
    }

    private void addFormRow(
            JPanel form,
            GridBagConstraints gbc,
            int row,
            String labelText,
            JTextField field
    ) {
        JLabel label = new JLabel(labelText);
        label.setForeground(textColor);
        label.setFont(new Font("Arial", Font.PLAIN, 13));

        field.setFont(new Font("Arial", Font.PLAIN, 14));
        field.setPreferredSize(new Dimension(200, 32));

        gbc.gridy = row;
        gbc.gridx = 0;
        gbc.gridwidth = 1;
        gbc.weightx = 0.35;
        form.add(label, gbc);

        gbc.gridx = 1;
        gbc.weightx = 0.65;
        form.add(field, gbc);
    }

    private void addGoal() {
        try {
            String goalName = nameField.getText().trim();

            if (goalName.isEmpty()) {
                JOptionPane.showMessageDialog(
                        this, "Enter a goal name."
                );
                return;
            }

            double target = Double.parseDouble(
                    amountField.getText().trim()
            );

            double saved = Double.parseDouble(
                    savedField.getText().trim()
            );

            String dateText = dateField.getText().trim();

            if (!Double.isFinite(target) ||
                    !Double.isFinite(saved) ||
                    target <= 0 || saved < 0 || saved > target) {
                JOptionPane.showMessageDialog(
                        this,
                        "Target must be positive, and saved amount must be between 0 and target."
                );
                return;
            }

            Date targetDate = null;

            if (!dateText.isEmpty()) {
                targetDate = Date.valueOf(
                        LocalDate.parse(dateText)
                );
            }

            FinancialGoal goal = new FinancialGoal(
                    userId, goalName, target, saved, targetDate
            );

            if (goalDAO.addGoal(goal)) {
                JOptionPane.showMessageDialog(
                        this, "Financial goal added successfully!"
                );

                nameField.setText("");
                amountField.setText("");
                savedField.setText("0");
                dateField.setText("");

                loadGoals();
            } else {
                JOptionPane.showMessageDialog(
                        this, "Unable to add goal."
                );
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(
                    this,
                    "Check the amounts and date. Use YYYY-MM-DD for the date."
            );
        }
    }

    private void loadGoals() {
        tableModel.setRowCount(0);

        List<FinancialGoal> goals =
                goalDAO.getGoalsByUser(userId);

        for (FinancialGoal goal : goals) {
            double remaining =
                    goal.getTargetAmount() - goal.getSavedAmount();

            double progress =
                    goal.getSavedAmount() / goal.getTargetAmount() * 100;

            tableModel.addRow(new Object[]{
                    goal.getId(),
                    goal.getGoalName(),
                    String.format("%.2f", goal.getTargetAmount()),
                    String.format("%.2f", goal.getSavedAmount()),
                    String.format("%.2f", remaining),
                    goal.getTargetDate(),
                    String.format("%.1f%%", progress)
            });
        }
    }
}