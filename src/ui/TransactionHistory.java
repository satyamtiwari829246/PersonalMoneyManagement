package ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.sql.Date;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import dao.Tranctiondao;

public class TransactionHistory extends JFrame {

    private final DefaultTableModel model;
    private final JTable table;

    private final JComboBox<String> typeFilter;
    private final JComboBox<String> categoryFilter;

    private final JTextField startDateField;
    private final JTextField endDateField;

    private final JLabel countLabel;

    private final List<Object[]> allTransactions =
            new ArrayList<>();

    public TransactionHistory(int userId) {

        setTitle("Transaction History - Kuber Manager");
        setSize(1100, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel mainPanel = new JPanel(new BorderLayout(12, 12));
        mainPanel.setBorder(
                BorderFactory.createEmptyBorder(20, 20, 20, 20)
        );
        mainPanel.setBackground(new Color(245, 247, 250));

        JLabel heading = new JLabel("Transaction History");
        heading.setFont(new Font("Arial", Font.BOLD, 26));

        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS));
        topPanel.setOpaque(false);
        topPanel.add(heading);
        topPanel.add(Box.createVerticalStrut(15));

        JPanel filterPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        filterPanel.setOpaque(false);

        typeFilter = new JComboBox<>(
                new String[]{"All", "Income", "Expense"}
        );

        categoryFilter = new JComboBox<>();
        categoryFilter.addItem("All");

        startDateField = new JTextField(10);
        endDateField = new JTextField(10);

        startDateField.setToolTipText("YYYY-MM-DD");
        endDateField.setToolTipText("YYYY-MM-DD");

        JButton filterButton = new JButton("Apply Filters");
        JButton resetButton = new JButton("Reset");

        filterPanel.add(new JLabel("Type:"));
        filterPanel.add(typeFilter);

        filterPanel.add(new JLabel("Category:"));
        filterPanel.add(categoryFilter);

        filterPanel.add(new JLabel("From (YYYY-MM-DD):"));
        filterPanel.add(startDateField);

        filterPanel.add(new JLabel("To (YYYY-MM-DD):"));
        filterPanel.add(endDateField);

        filterPanel.add(filterButton);
        filterPanel.add(resetButton);

        topPanel.add(filterPanel);
        mainPanel.add(topPanel, BorderLayout.NORTH);

        String[] columns = {
                "ID", "Category", "Type",
                "Amount", "Date", "Description"
        };

        model = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        table = new JTable(model);
        table.setRowHeight(28);
        table.setAutoCreateRowSorter(true);

        mainPanel.add(new JScrollPane(table), BorderLayout.CENTER);

        countLabel = new JLabel("Transactions: 0");
        countLabel.setFont(new Font("Arial", Font.BOLD, 15));

        mainPanel.add(countLabel, BorderLayout.SOUTH);

        filterButton.addActionListener(e -> applyFilters());
        resetButton.addActionListener(e -> resetFilters());

        loadTransactions(userId);

        add(mainPanel);
        setVisible(true);
    }

    private void loadTransactions(int userId) {

        try {
            Tranctiondao dao = new Tranctiondao();
            ResultSet rs = dao.getAllTransactions(userId);

            if (rs == null) {
                JOptionPane.showMessageDialog(
                        this,
                        "Could not load transactions."
                );
                return;
            }

            while (rs.next()) {

                int id = rs.getInt("id");
                String category = rs.getString("category");
                String type = rs.getString("type");
                double amount = rs.getDouble("amount");
                Date date = rs.getDate("transaction_date");
                String description = rs.getString("description");

                Object[] row = {
                        id, category, type, amount, date, description
                };

                allTransactions.add(row);

                if (category != null &&
                        !category.isBlank() &&
                        !containsCategory(category)) {
                    categoryFilter.addItem(category);
                }
            }

            rs.close();
            applyFilters();

        } catch (Exception e) {
            e.printStackTrace();

            JOptionPane.showMessageDialog(
                    this,
                    "Unable to load transactions: " + e.getMessage(),
                    "Database Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    private boolean containsCategory(String category) {

        for (int i = 0; i < categoryFilter.getItemCount(); i++) {
            if (category.equals(categoryFilter.getItemAt(i))) {
                return true;
            }
        }

        return false;
    }
      // filtering the tranction
    private void applyFilters() {

        LocalDate startDate;
        LocalDate endDate;

        try {
            startDate = parseDate(startDateField.getText().trim());
            endDate = parseDate(endDateField.getText().trim());

            if (startDate != null && endDate != null
                    && startDate.isAfter(endDate)) {

                JOptionPane.showMessageDialog(
                        this,
                        "Start date cannot be after end date."
                );
                return;
            }

        } catch (DateTimeParseException e) {
            JOptionPane.showMessageDialog(
                    this,
                    "Enter dates in YYYY-MM-DD format, for example 2026-10-09."
            );
            return;
        }

        String selectedType =
                (String) typeFilter.getSelectedItem();

        String selectedCategory =
                (String) categoryFilter.getSelectedItem();

        model.setRowCount(0);

        int count = 0;

        for (Object[] row : allTransactions) {

            String type = String.valueOf(row[2]);
            String category = row[1] == null
                    ? ""
                    : String.valueOf(row[1]);

            LocalDate transactionDate = row[4] == null
                    ? null
                    : ((Date) row[4]).toLocalDate();

            boolean typeMatches =
                    "All".equals(selectedType)
                            || selectedType.equalsIgnoreCase(type);

            boolean categoryMatches =
                    "All".equals(selectedCategory)
                            || selectedCategory.equals(category);

            boolean startMatches =
                    startDate == null
                            || (transactionDate != null
                            && !transactionDate.isBefore(startDate));

            boolean endMatches =
                    endDate == null
                            || (transactionDate != null
                            && !transactionDate.isAfter(endDate));

            if (typeMatches && categoryMatches
                    && startMatches && endMatches) {

                model.addRow(row);
                count++;
            }
        }

        countLabel.setText("Transactions: " + count);
    }

    private LocalDate parseDate(String value) {

        if (value.isEmpty()) {
            return null;
        }

        return LocalDate.parse(value);
    }

    private void resetFilters() {

        typeFilter.setSelectedItem("All");
        categoryFilter.setSelectedItem("All");
        startDateField.setText("");
        endDateField.setText("");

        applyFilters();
    }
}

