package jdbc;

import java.sql.*;

class JdbcBatching {

    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/mydb";
        String user = "root";
        String password = "password";
        try (Connection conn = DriverManager.getConnection(url, user, password)) {
            String insertSQL = "INSERT INTO users (name, email) VALUES (?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(insertSQL);
            String[][] users = {
                    {"Charlie", "charlie@example.com"},
                    {"David", "david@example.com"},
                    {"Eve", "eve@example.com"},
                    {"Frank", "frank@example.com"},
                    {"Grace", "grace@example.com"}
            };
            long startTime = System.currentTimeMillis();
            for (String[] userData : users) {
                pstmt.setString(1, userData[0]);
                pstmt.setString(2, userData[1]);
                pstmt.addBatch();
            }
            int[] results = pstmt.executeBatch();
            long endTime = System.currentTimeMillis();
            System.out.println("Batch execution completed!");
            System.out.println("Total records inserted: " + results.length);
            System.out.println("Time taken: " + (endTime - startTime) + " ms");
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT COUNT(*) as total FROM users");
            if (rs.next()) {
                System.out.println("Total users in database: " + rs.getInt("total"));
            }
            rs.close();
            stmt.close();
            pstmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}