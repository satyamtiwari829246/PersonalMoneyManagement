  
        package database;

import java.sql.Connection;//databse package
import java.sql.DriverManager;

public class DBConnection {

    static String url =
            "jdbc:mysql://localhost:3306/money_management";

    static String username = "root";

    static String password = "12345678";

    public static Connection con() {

        try {

            Class.forName(
                    "com.mysql.cj.jdbc.Driver"
            );

            Connection connection =
                    DriverManager.getConnection(
                            url,
                            username,
                            password
                    );

//            System.out.println(
//                    "Database connected successfully!"
//            );

            return connection;

        } catch (Exception e) {

            System.out.println(
                    "Database connection failed!"
            );

            e.printStackTrace();

            return null;
        }
    }

    public static void main(String[] args) {

        con();

    }
}

