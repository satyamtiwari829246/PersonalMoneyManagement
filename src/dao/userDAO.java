package dao;

import  database.DBConnection;
import model.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class userDAO {
    public static boolean createUser(User User){
        String sql="INSERT INTO users (name, email, password) VALUES (?, ?, ?)";
        try {
            Connection connection = DBConnection.con();
            PreparedStatement statement= connection.prepareStatement(sql);
            statement.setString(1,User.getName());
            statement.setString(2, User.getEmail());
            statement.setString(3, User.getPassword());
            int rows= statement.executeUpdate();
            statement.close();
            connection.close();
            return rows>0;
        } catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }
    public  boolean loginuser (String email,String password){
        String sql= "SELECT * FROM users WHERE email = ? AND password = ?";
        try{
            Connection connection= DBConnection.con();
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1,email);
            statement.setString(2,password);
            ResultSet result = statement.executeQuery();
            boolean found = result.next();
            result.close();
            statement.close();
            connection.close();
           return found;
        }  catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public int getUserId(String email, String password) {

        String sql =
                "SELECT id FROM users WHERE email=? AND password=?";

        try {

            Connection connection2 =
                    DBConnection.con();

            PreparedStatement statement2 =
                    connection2.prepareStatement(sql);

            statement2.setString(1, email);
            statement2.setString(2, password);

            ResultSet result2 =
                    statement2.executeQuery();

            if (result2.next()) {

                int userId =
                        result2.getInt("id");

                result2.close();
                statement2.close();
                connection2.close();

                return userId;
            }

            result2.close();
            statement2.close();
            connection2.close();

            return -1;

        } catch (Exception e) {

            e.printStackTrace();

            return -1;
        }
    }
}
