
        package ui;

import dao.Tranctiondao;
import model.Tranction;

import javax.swing.*;
import java.awt.*;
import java.sql.Date;

public class Addtransaction extends JFrame {

    private JComboBox<String> typeBox;
    private JTextField amountField;
    private JComboBox<String> categoryBox;
    private JTextField descriptionField;
    private JTextField dateField;

    // For now we will use a test user ID.
    // Later we will get this from the logged-in user.
    private final int userId;

    public Addtransaction(int userId) {
        this.userId = userId;

        setTitle("KuberManager - Add Transaction");
        setSize(500, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(
                new BoxLayout(mainPanel, BoxLayout.Y_AXIS)
        );

        mainPanel.setBackground(
                new Color(15, 23, 42)
        );

        mainPanel.setBorder(
                BorderFactory.createEmptyBorder(
                        35, 50, 35, 50
                )
        );

        // ================= TITLE =================

        JLabel title =
                new JLabel("Add Transaction");

        title.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        28
                )
        );

        title.setForeground(Color.WHITE);

        title.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

        mainPanel.add(title);

        mainPanel.add(
                Box.createVerticalStrut(30)
        );


        // ================= TYPE =================

        JLabel typeLabel =
                createLabel("Transaction Type");

        mainPanel.add(typeLabel);

        mainPanel.add(
                Box.createVerticalStrut(8)
        );

        typeBox =
                new JComboBox<>(
                        new String[]{
                                "Income",
                                "Expense"
                        }
                );

        styleComboBox(typeBox);

        mainPanel.add(typeBox);

        mainPanel.add(
                Box.createVerticalStrut(20)
        );


        // ================= AMOUNT =================

        JLabel amountLabel =
                createLabel("Amount");

        mainPanel.add(amountLabel);

        mainPanel.add(
                Box.createVerticalStrut(8)
        );

        amountField =
                new JTextField();

        styleTextField(amountField);

        mainPanel.add(amountField);

        mainPanel.add(
                Box.createVerticalStrut(20)
        );


        // ================= CATEGORY =================

        JLabel categoryLabel =
                createLabel("Category");

        mainPanel.add(categoryLabel);

        mainPanel.add(
                Box.createVerticalStrut(8)
        );

        categoryBox =
                new JComboBox<>(
                        new String[]{
                                "Food",
                                "Shopping",
                                "Transport",
                                "Bills",
                                "Entertainment",
                                "Salary",
                                "Freelance",
                                "Other"
                        }
                );

        styleComboBox(categoryBox);

        mainPanel.add(categoryBox);

        mainPanel.add(
                Box.createVerticalStrut(20)
        );


        // ================= DESCRIPTION =================

        JLabel descriptionLabel =
                createLabel("Description");

        mainPanel.add(descriptionLabel);

        mainPanel.add(
                Box.createVerticalStrut(8)
        );

        descriptionField =
                new JTextField();

        styleTextField(descriptionField);

        mainPanel.add(descriptionField);

        mainPanel.add(
                Box.createVerticalStrut(20)
        );


        // ================= DATE =================

        JLabel dateLabel =
                createLabel(
                        "Date (YYYY-MM-DD)"
                );

        mainPanel.add(dateLabel);

        mainPanel.add(
                Box.createVerticalStrut(8)
        );

        dateField =
                new JTextField();

        dateField.setText(
                new Date(
                        System.currentTimeMillis()
                ).toString()
        );

        styleTextField(dateField);

        mainPanel.add(dateField);

        mainPanel.add(
                Box.createVerticalStrut(30)
        );


        // ================= SAVE BUTTON =================

        JButton saveButton =
                new JButton("SAVE TRANSACTION");

        saveButton.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        14
                )
        );

        saveButton.setForeground(
                Color.WHITE
        );

        saveButton.setBackground(
                new Color(34, 197, 94)
        );

        saveButton.setFocusPainted(false);

        saveButton.setBorderPainted(false);

        saveButton.setCursor(
                new Cursor(
                        Cursor.HAND_CURSOR
                )
        );

        saveButton.setMaximumSize(
                new Dimension(
                        Integer.MAX_VALUE,
                        45
                )
        );

        saveButton.addActionListener(
                e -> saveTransaction()
        );

        mainPanel.add(saveButton);

        add(mainPanel);

        setVisible(true);
    }


    // ================= SAVE TRANSACTION =================

    private void saveTransaction() {

        try {

            String type =
                    (String) typeBox.getSelectedItem();

            String amountText =
                    amountField.getText().trim();

            String category =
                    (String) categoryBox.getSelectedItem();

            String description =
                    descriptionField
                            .getText()
                            .trim();

            String dateText =
                    dateField
                            .getText()
                            .trim();


            // Empty amount check

            if (amountText.isEmpty()) {

                JOptionPane.showMessageDialog(
                        this,
                        "Please enter amount."
                );

                return;
            }


            // Convert amount


            double amount = Double.parseDouble(amountText);

            if (amount <= 0) {
                JOptionPane.showMessageDialog(
                        this,
                        "Amount must be greater than zero.",
                        "Invalid Amount",
                        JOptionPane.WARNING_MESSAGE
                );
                return;
            }


            // Convert date

            Date date =
                    Date.valueOf(dateText);


            // Create Transaction object

            Tranction transaction =
                    new Tranction(
                            userId,
                            type,
                            amount,
                            category,
                            description,
                            date
                    );


            // DAO

            Tranctiondao dao =
                    new Tranctiondao();


            // Save to database

            boolean result =
                    dao.addTransaction(
                            transaction
                    );


            if (result) {

                JOptionPane.showMessageDialog(
                        this,
                        "Transaction Added Successfully!"
                );

                amountField.setText("");
                descriptionField.setText("");

            } else {

                JOptionPane.showMessageDialog(
                        this,
                        "Failed to save transaction."
                );
            }


        } catch (NumberFormatException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please enter a valid amount."
            );

        } catch (IllegalArgumentException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please enter date in YYYY-MM-DD format."
            );

        } catch (Exception e) {

            e.printStackTrace();

            JOptionPane.showMessageDialog(
                    this,
                    "Something went wrong."
            );
        }
    }


    // ================= LABEL =================

    private JLabel createLabel(String text) {

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
                Color.WHITE
        );

        label.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

        return label;
    }


    // ================= TEXT FIELD =================

    private void styleTextField(
            JTextField field
    ) {

        field.setFont(
                new Font(
                        "Arial",
                        Font.PLAIN,
                        14
                )
        );

        field.setForeground(
                Color.WHITE
        );

        field.setBackground(
                new Color(51, 65, 85)
        );

        field.setCaretColor(
                Color.WHITE
        );

        field.setBorder(
                BorderFactory.createEmptyBorder(
                        10, 12, 10, 12
                )
        );

        field.setMaximumSize(
                new Dimension(
                        Integer.MAX_VALUE,
                        42
                )
        );
    }


    // ================= COMBO BOX =================

    private void styleComboBox(
            JComboBox<String> box
    ) {

        box.setFont(
                new Font(
                        "Arial",
                        Font.PLAIN,
                        14
                )
        );

        box.setForeground(
                Color.WHITE
        );

        box.setBackground(
                new Color(51, 65, 85)
        );

        box.setMaximumSize(
                new Dimension(
                        Integer.MAX_VALUE,
                        42
                )
        );
    }



}

