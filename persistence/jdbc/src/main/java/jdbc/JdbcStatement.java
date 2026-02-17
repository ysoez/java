package jdbc;

import java.sql.*;

class JdbcStatement {

    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/mydb";
        String user = "root";
        String password = "password";
        try (Connection conn = DriverManager.getConnection(url, user, password);
             Statement stmt = conn.createStatement()) {
            String createTable = "CREATE TABLE IF NOT EXISTS users (" +
                    "id INT PRIMARY KEY AUTO_INCREMENT, " +
                    "name VARCHAR(100), " +
                    "email VARCHAR(100))";
            stmt.execute(createTable);

            stmt.executeUpdate("INSERT INTO users (name, email) VALUES ('Alice', 'alice@example.com')");
            stmt.executeUpdate("INSERT INTO users (name, email) VALUES ('Bob', 'bob@example.com')");

            ResultSet rs = stmt.executeQuery("SELECT * FROM users");
            System.out.println("\nUsers:");
            while (rs.next()) {
                System.out.println("ID: " + rs.getInt("id") +
                        ", Name: " + rs.getString("name") +
                        ", Email: " + rs.getString("email"));
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}