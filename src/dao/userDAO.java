
package dao;

import database.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import model.User;

public class userDAO {

    // REGISTER USER
    public static boolean createUser(User user) {
        String sql = "INSERT INTO users (name, email, password) VALUES (?, ?, ?)";

        try (
                Connection connection = DBConnection.con();
                PreparedStatement statement = connection.prepareStatement(sql)
        ) {
            statement.setString(1, user.getName());
            statement.setString(2, user.getEmail());
            statement.setString(3, user.getPassword());

            return statement.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // LOGIN: BLOCKED USERS CANNOT LOGIN
    public boolean loginuser(String email, String password) {
        String sql = "SELECT id FROM users " +
                "WHERE email = ? AND password = ? " +
                "AND status <> 'BLOCKED'";

        try (
                Connection connection = DBConnection.con();
                PreparedStatement statement = connection.prepareStatement(sql)
        ) {
            statement.setString(1, email);
            statement.setString(2, password);

            try (ResultSet result = statement.executeQuery()) {
                return result.next();
            }

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // GET USER ID
    public int getUserId(String email, String password) {
        String sql = "SELECT id FROM users " +
                "WHERE email = ? AND password = ? " +
                "AND status <> 'BLOCKED'";

        try (
                Connection connection = DBConnection.con();
                PreparedStatement statement = connection.prepareStatement(sql)
        ) {
            statement.setString(1, email);
            statement.setString(2, password);

            try (ResultSet result = statement.executeQuery()) {
                if (result.next()) {
                    return result.getInt("id");
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return -1;
    }

    // GET USER ROLE
    public String getUserRole(String email, String password) {
        String sql = "SELECT role FROM users " +
                "WHERE email = ? AND password = ? " +
                "AND status <> 'BLOCKED'";

        try (
                Connection connection = DBConnection.con();
                PreparedStatement statement = connection.prepareStatement(sql)
        ) {
            statement.setString(1, email);
            statement.setString(2, password);

            try (ResultSet result = statement.executeQuery()) {
                if (result.next()) {
                    return result.getString("role");
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    // ADMIN REGISTRATION
    public boolean registerAdmin(
            String name,
            String email,
            String password,
            String verificationCode
    ) {
        final String ADMIN_SECRET_CODE = "829246";

        if (verificationCode == null ||
                !ADMIN_SECRET_CODE.equals(verificationCode)) {
            return false;
        }

        String sql = "INSERT INTO users (name, email, password, role) " +
                "VALUES (?, ?, ?, 'Admin')";

        try (
                Connection connection = DBConnection.con();
                PreparedStatement statement = connection.prepareStatement(sql)
        ) {
            statement.setString(1, name);
            statement.setString(2, email);
            statement.setString(3, password);

            return statement.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // FETCH USERS FOR THE ADMIN SECURITY SCREEN
    public List<Map<String, Object>> getAllUsers() {
        List<Map<String, Object>> users = new ArrayList<>();

        String sql = "SELECT id, name, email, role, status " +
                "FROM users ORDER BY id";

        try (
                Connection connection = DBConnection.con();
                PreparedStatement statement = connection.prepareStatement(sql);
                ResultSet result = statement.executeQuery()
        ) {
            while (result.next()) {
                Map<String, Object> user = new HashMap<>();

                user.put("id", result.getInt("id"));
                user.put("name", result.getString("name"));
                user.put("email", result.getString("email"));
                user.put("role", result.getString("role"));
                user.put("status", result.getString("status"));

                users.add(user);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return users;
    }

    // RESTRICT, BLOCK, OR UNBLOCK A NORMAL USER
    public boolean updateUserStatus(int userId, String newStatus) {

        if (newStatus == null ||
                !(newStatus.equals("ACTIVE") ||
                        newStatus.equals("RESTRICTED") ||
                        newStatus.equals("BLOCKED"))) {
            return false;
        }

        // Only normal User accounts can be changed.
        // Admin accounts cannot be blocked using this method.
        String sql = "UPDATE users SET status = ? " +
                "WHERE id = ? AND role = 'User'";

        try (
                Connection connection = DBConnection.con();
                PreparedStatement statement = connection.prepareStatement(sql)
        ) {
            statement.setString(1, newStatus);
            statement.setInt(2, userId);

            return statement.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}