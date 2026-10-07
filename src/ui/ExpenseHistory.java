package ui;

import dao.Tranctiondao;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.ResultSet;

public class ExpenseHistory extends JFrame {

    private int userId;

    private JTable table;
    private DefaultTableModel model;

    private JLabel totalLabel;

    public ExpenseHistory(int userId) {

        this.userId = userId;

        setTitle("KuberManager - Expense History");
        setSize(850, 550);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new BorderLayout(15, 15));

        mainPanel.setBackground(
                new Color(15, 23, 42)
        );

        mainPanel.setBorder(
                BorderFactory.createEmptyBorder(
                        25, 25, 25, 25
                )
        );

        // ================= TITLE =================

        JLabel title =
                new JLabel("Expense History");

        title.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        28
                )
        );

        title.setForeground(Color.WHITE);

        mainPanel.add(
                title,
                BorderLayout.NORTH
        );


        // ================= TABLE =================

        model = new DefaultTableModel();

        model.addColumn("ID");
        model.addColumn("Amount");
        model.addColumn("Category");
        model.addColumn("Description");
        model.addColumn("Date");

        table = new JTable(model);

        table.setFont(
                new Font(
                        "Arial",
                        Font.PLAIN,
                        14
                )
        );

        table.setRowHeight(35);

        table.setBackground(
                new Color(30, 41, 59)
        );

        table.setForeground(Color.WHITE);

        table.setSelectionBackground(
                new Color(34, 197, 94)
        );

        table.setSelectionForeground(Color.WHITE);

        table.getTableHeader().setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        14
                )
        );

        table.getTableHeader().setBackground(
                new Color(51, 65, 85)
        );

        table.getTableHeader().setForeground(
                Color.WHITE
        );

        JScrollPane scrollPane =
                new JScrollPane(table);

        mainPanel.add(
                scrollPane,
                BorderLayout.CENTER
        );


        // ================= BOTTOM =================

        JPanel bottomPanel =
                new JPanel(
                        new FlowLayout(
                                FlowLayout.RIGHT
                        )
                );

        bottomPanel.setOpaque(false);

        totalLabel =
                new JLabel("Total Expenses: ₹0");

        totalLabel.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        18
                )
        );
        JButton deleteButton =
                new JButton("Delete Selected");

        deleteButton.setFont(
                new Font("Arial", Font.BOLD, 13)
        );

        deleteButton.setForeground(Color.WHITE);

        deleteButton.setBackground(
                new Color(239, 68, 68)
        );

        deleteButton.setFocusPainted(false);

        deleteButton.setBorderPainted(false);

        deleteButton.addActionListener(
                e -> deleteSelectedExpense()
        );

        bottomPanel.add(deleteButton);

        totalLabel.setForeground(
                new Color(34, 197, 94)
        );

        bottomPanel.add(totalLabel);

        mainPanel.add(
                bottomPanel,
                BorderLayout.SOUTH
        );


        add(mainPanel);

        loadExpenses();

        setVisible(true);
    }

    private void deleteSelectedExpense() {

        int selectedRow =
                table.getSelectedRow();

        if (selectedRow == -1) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please select an expense first.",
                    "Delete Expense",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }


        int expenseId =
                (int) model.getValueAt(
                        selectedRow,
                        0
                );


        int confirm =
                JOptionPane.showConfirmDialog(
                        this,
                        "Are you sure you want to delete this expense?",
                        "Confirm Delete",
                        JOptionPane.YES_NO_OPTION
                );


        if (confirm != JOptionPane.YES_OPTION) {

            return;
        }


        Tranctiondao dao =
                new Tranctiondao();


        boolean deleted =
                dao.deleteExpense(
                        expenseId,
                        userId
                );


        if (deleted) {

            JOptionPane.showMessageDialog(
                    this,
                    "Expense deleted successfully!",
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE
            );

            model.removeRow(selectedRow);

            calculateTotal();

        } else {

            JOptionPane.showMessageDialog(
                    this,
                    "Unable to delete expense.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }
     //calculate total method
     private void calculateTotal() {

         double total = 0;

         for (int i = 0; i < model.getRowCount(); i++) {

             String amountText =
                     model.getValueAt(i, 1)
                             .toString()
                             .replace("₹", "")
                             .trim();

             total += Double.parseDouble(amountText);
         }

         totalLabel.setText(
                 "Total Expenses: ₹" + total
         );
     }


    // ================= LOAD EXPENSES =================

    private void loadExpenses() {

        Tranctiondao dao =
                new Tranctiondao();

        ResultSet result =
                dao.getExpensesByUserId(userId);

        double total = 0;

        try {

            while (result != null && result.next()) {

                int id =
                        result.getInt("id");

                double amount =
                        result.getDouble("amount");

                String category =
                        result.getString("category");

                String description =
                        result.getString("description");

                String date =
                        result.getString(
                                "transaction_date"
                        );

                model.addRow(
                        new Object[]{
                                id,
                                "₹ " + amount,
                                category,
                                description,
                                date
                        }
                );

                total += amount;
            }

            totalLabel.setText(
                    "Total Expenses: ₹" + total
            );

        } catch (Exception e) {

            e.printStackTrace();

            JOptionPane.showMessageDialog(
                    this,
                    "Unable to load expenses."
            );
        }
    }


    // ================= MAIN =================

    public static void main(String[] args) {

        SwingUtilities.invokeLater(
                () -> new ExpenseHistory(1)
        );
    }

}
