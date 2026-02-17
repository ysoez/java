package jdbc;

import java.sql.*;
import java.util.Scanner;

class JdbcPreparedStatement {

    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/mydb";
        String user = "root";
        String password = "password";
        
        Scanner scanner = new Scanner(System.in);
        try (Connection conn = DriverManager.getConnection(url, user, password)) {
            String insertSQL = "INSERT INTO users (name, email) VALUES (?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(insertSQL);
            
            System.out.print("Enter name: ");
            String name = scanner.nextLine();
            System.out.print("Enter email: ");
            String email = scanner.nextLine();
            
            pstmt.setString(1, name);
            pstmt.setString(2, email);
            
            int rowsAffected = pstmt.executeUpdate();
            System.out.println(rowsAffected + " row(s) inserted!");

            String querySQL = "SELECT * FROM users WHERE name LIKE ?";
            PreparedStatement queryStmt = conn.prepareStatement(querySQL);
            queryStmt.setString(1, "%" + name + "%");
            
            ResultSet rs = queryStmt.executeQuery();
            System.out.println("\nSearch Results:");
            while (rs.next()) {
                System.out.println("ID: " + rs.getInt("id") + 
                                 ", Name: " + rs.getString("name") + 
                                 ", Email: " + rs.getString("email"));
            }
            
            rs.close();
            queryStmt.close();
            pstmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        scanner.close();
    }
}