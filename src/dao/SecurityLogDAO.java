
package dao;

import database.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class SecurityLogDAO {

    public boolean addLog(Integer userId, String action, String details) {
        String sql = "INSERT INTO security_logs (user_id, action, details) VALUES (?, ?, ?)";

        try (Connection con = DBConnection.con();
             PreparedStatement ps = con.prepareStatement(sql)) {

            if (userId == null) {
                ps.setNull(1, java.sql.Types.INTEGER);
            } else {
                ps.setInt(1, userId);
            }

            ps.setString(2, action);
            ps.setString(3, details);

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public ResultSet getAllLogs() throws Exception {
        Connection con = DBConnection.con();
        String sql = "SELECT id, user_id, action, details, created_at " +
                "FROM security_logs ORDER BY created_at DESC";

        PreparedStatement ps = con.prepareStatement(sql);
        return ps.executeQuery();
    }
}