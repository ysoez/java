package jdbc;

import java.sql.*;

class JdbcCallableStatement {

    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/mydb";
        String user = "root";
        String password = "password";

        try (Connection conn = DriverManager.getConnection(url, user, password)) {

            // Create a stored procedure
            Statement stmt = conn.createStatement();
            stmt.execute("DROP PROCEDURE IF EXISTS getUserById");

            String createProc = "CREATE PROCEDURE getUserById(IN userId INT) " +
                    "BEGIN " +
                    "  SELECT * FROM users WHERE id = userId; " +
                    "END";
            stmt.execute(createProc);
            System.out.println("Stored procedure created!");

            // Call the stored procedure
            CallableStatement cstmt = conn.prepareCall("{call getUserById(?)}");
            cstmt.setInt(1, 1);

            ResultSet rs = cstmt.executeQuery();
            System.out.println("\nCalling stored procedure getUserById(1):");
            while (rs.next()) {
                System.out.println("ID: " + rs.getInt("id") +
                        ", Name: " + rs.getString("name") +
                        ", Email: " + rs.getString("email"));
            }

            // Create procedure with OUT parameter
            stmt.execute("DROP PROCEDURE IF EXISTS getUserCount");
            String createCountProc = "CREATE PROCEDURE getUserCount(OUT userCount INT) " +
                    "BEGIN " +
                    "  SELECT COUNT(*) INTO userCount FROM users; " +
                    "END";
            stmt.execute(createCountProc);

            // Call procedure with OUT parameter
            CallableStatement cstmt2 = conn.prepareCall("{call getUserCount(?)}");
            cstmt2.registerOutParameter(1, Types.INTEGER);
            cstmt2.execute();

            int count = cstmt2.getInt(1);
            System.out.println("\nTotal users (from OUT parameter): " + count);

            rs.close();
            cstmt.close();
            cstmt2.close();
            stmt.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}