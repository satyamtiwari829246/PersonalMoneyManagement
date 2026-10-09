
package dao;

import database.DBConnection;
import model.FinancialGoal;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class GoalDAO {

    public boolean addGoal(FinancialGoal goal) {
        String sql = "INSERT INTO financial_goals " +
                "(user_id, goal_name, target_amount, saved_amount, target_date) " +
                "VALUES (?, ?, ?, ?, ?)";

        try (Connection con = DBConnection.con();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, goal.getUserId());
            ps.setString(2, goal.getGoalName());
            ps.setDouble(3, goal.getTargetAmount());
            ps.setDouble(4, goal.getSavedAmount());

            if (goal.getTargetDate() != null) {
                ps.setDate(5, goal.getTargetDate());
            } else {
                ps.setNull(5, Types.DATE);
            }

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<FinancialGoal> getGoalsByUser(int userId) {
        List<FinancialGoal> goals = new ArrayList<>();

        String sql = "SELECT * FROM financial_goals WHERE user_id = ? ORDER BY id DESC";

        try (Connection con = DBConnection.con();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, userId);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    FinancialGoal goal = new FinancialGoal(
                            rs.getInt("user_id"),
                            rs.getString("goal_name"),
                            rs.getDouble("target_amount"),
                            rs.getDouble("saved_amount"),
                            rs.getDate("target_date")
                    );

                    goal.setId(rs.getInt("id"));
                    goals.add(goal);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return goals;
    }
}