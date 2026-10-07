package ui;

import dao.Tranctiondao;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.ResultSet;

public class IncomeHistory extends JFrame {

    private int userId;
    private JTable table;
    private DefaultTableModel model;

    public IncomeHistory(int userId) {

        this.userId = userId;

        setTitle("Income History");
        setSize(800, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        model = new DefaultTableModel(
                new Object[]{
                        "ID",
                        "Amount",
                        "Category",
                        "Description",
                        "Date"
                }, 0
        );

        table = new JTable(model);

        add(new JScrollPane(table));

        loadIncome();

        setVisible(true);
    }

    private void loadIncome() {

        String sql =
                "SELECT * FROM transactions " +
                        "WHERE user_id=? AND type='Income' " +
                        "ORDER BY transaction_date DESC";

        try {

            java.sql.Connection connection =
                    database.DBConnection.con();

            java.sql.PreparedStatement statement =
                    connection.prepareStatement(sql);

            statement.setInt(1, userId);

            ResultSet result =
                    statement.executeQuery();

            while (result.next()) {

                model.addRow(new Object[]{

                        result.getInt("id"),

                        result.getDouble("amount"),

                        result.getString("category"),

                        result.getString("description"),

                        result.getDate("transaction_date")
                });
            }

            result.close();
            statement.close();
            connection.close();

        } catch (Exception e) {

            e.printStackTrace();

            JOptionPane.showMessageDialog(
                    this,
                    "Unable to load income."
            );
        }
    }
}