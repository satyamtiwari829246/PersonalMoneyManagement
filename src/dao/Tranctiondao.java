
        package dao;

import database.DBConnection;
import model.Tranction;
import java.sql.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

        public class Tranctiondao {

    public boolean addTransaction(Tranction transaction) {

        String sql =
                "INSERT INTO transactions " +
                        "(user_id, type, amount, category, description, transaction_date) " +
                        "VALUES (?, ?, ?, ?, ?, ?)";

        try {

            Connection connection =
                    DBConnection.con();

            PreparedStatement statement =
                    connection.prepareStatement(sql);

            statement.setInt(
                    1,
                    transaction.getUserId()
            );

            statement.setString(
                    2,
                    transaction.getType()
            );

            statement.setDouble(
                    3,
                    transaction.getAmount()
            );

            statement.setString(
                    4,
                    transaction.getCategory()
            );

            statement.setString(
                    5,
                    transaction.getDescription()
            );

            statement.setDate(
                    6,
                    transaction.getTransactionDate()
            );

            int rows =
                    statement.executeUpdate();

            statement.close();
            connection.close();

            return rows > 0;

        } catch (Exception e) {

            e.printStackTrace();

            return false;
        }
    }
    public ResultSet getExpensesByUserId(int userId) {

        String sql =
                "SELECT * FROM transactions " +
                        "WHERE user_id = ? AND type = 'Expense' " +
                        "ORDER BY transaction_date DESC";

        try {

            Connection connection = DBConnection.con();

            PreparedStatement statement =
                    connection.prepareStatement(sql);

            statement.setInt(1, userId);

            return statement.executeQuery();

        } catch (Exception e) {

            e.printStackTrace();

            return null;
        }
    }
            public boolean deleteExpense(int expenseId, int userId) {

                String sql =
                        "DELETE FROM transactions " +
                                "WHERE id = ? AND user_id = ? AND type = 'Expense'";

                try {

                    Connection connection = DBConnection.con();

                    PreparedStatement statement =
                            connection.prepareStatement(sql);

                    statement.setInt(1, expenseId);
                    statement.setInt(2, userId);

                    int rows = statement.executeUpdate();

                    statement.close();
                    connection.close();

                    return rows > 0;

                } catch (Exception e) {

                    e.printStackTrace();

                    return false;
                }
            }
            public double getTotalIncome(int userId) {

                String sql = "SELECT COALESCE(SUM(amount), 0) " +
                        "FROM transactions " +
                        "WHERE user_id=? AND type='Income'";

                try (Connection connection = DBConnection.con();
                     PreparedStatement statement = connection.prepareStatement(sql)) {

                    statement.setInt(1, userId);

                    ResultSet result = statement.executeQuery();

                    if (result.next()) {
                        return result.getDouble(1);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }

                return 0;
            }
            public double getTotalExpense(int userId) {

                String sql = "SELECT COALESCE(SUM(amount), 0) " +
                        "FROM transactions " +
                        "WHERE user_id=? AND type='Expense'";

                try (Connection connection = DBConnection.con();
                     PreparedStatement statement = connection.prepareStatement(sql)) {

                    statement.setInt(1, userId);

                    ResultSet result = statement.executeQuery();

                    if (result.next()) {
                        return result.getDouble(1);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }

                return 0;
            }
}

