package dao;

import database.DBConnection;
import model.Budget;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class BudgetDAO {

    // Add Budget
    public boolean addBudget(Budget budget) {

        String sql = "INSERT INTO budgets " + "(user_id, category, amount, duration) " + "VALUES (?, ?, ?, ?)";

        try (
                Connection connection = DBConnection.con();
                PreparedStatement statement =
                        connection.prepareStatement(sql)
        ) {

            statement.setInt(1, budget.getUserId());
            statement.setString(2, budget.getCategory());
            statement.setDouble(3, budget.getAmount());
            statement.setString(4, budget.getDuration());

            int rows = statement.executeUpdate();

            return rows > 0;

        } catch (Exception e) {

            e.printStackTrace();

            return false;
        }
    }

    // Get all budgets of a user
    public ResultSet getBudgetsByUserId(int userId) {

        String sql =
                "SELECT * FROM budgets " +
                        "WHERE user_id = ?";

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

    // Delete the budget
    public boolean deleteBudget(int budgetId, int userId) {

        String sql =
                "DELETE FROM budgets " +
                        "WHERE id = ? AND user_id = ?";

        try (
                Connection connection = DBConnection.con();
                PreparedStatement statement =
                        connection.prepareStatement(sql)
        ) {

            statement.setInt(1, budgetId);
            statement.setInt(2, userId);

            int rows = statement.executeUpdate();

            return rows > 0;

        } catch (Exception e) {

            e.printStackTrace();

            return false;
        }
    }
    public double getSpentAmount(int userId, String category) {

        String sql =
                "SELECT COALESCE(SUM(amount), 0) " +
                        "FROM transactions " +
                        "WHERE user_id = ? " +
                        "AND category = ? " +
                        "AND type = 'Expense'";

        try (
                Connection connection = DBConnection.con();
                PreparedStatement statement =
                        connection.prepareStatement(sql)
        ) {

            statement.setInt(1, userId);
            statement.setString(2, category);

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