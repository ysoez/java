package jdbc;

import java.sql.*;

class JdbcTransaction {

    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/mydb";
        String user = "root";
        String password = "password";
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url, user, password);
            // Create accounts table
            Statement setup = conn.createStatement();
            setup.execute("CREATE TABLE IF NOT EXISTS accounts (" +
                         "id INT PRIMARY KEY, " +
                         "name VARCHAR(100), " +
                         "balance DECIMAL(10,2))");
            setup.execute("DELETE FROM accounts");
            setup.execute("INSERT INTO accounts VALUES (1, 'Account A', 1000.00)");
            setup.execute("INSERT INTO accounts VALUES (2, 'Account B', 500.00)");
            setup.close();
            // Display initial balances
            System.out.println("Initial Balances:");
            displayBalances(conn);
            // Start transaction
            conn.setAutoCommit(false);
            try {
                Statement stmt = conn.createStatement();
                // Transfer $200 from Account A to Account B
                int rows1 = stmt.executeUpdate(
                    "UPDATE accounts SET balance = balance - 200 WHERE id = 1");
                System.out.println("\nDeducted $200 from Account A");
                int rows2 = stmt.executeUpdate(
                    "UPDATE accounts SET balance = balance + 200 WHERE id = 2");
                System.out.println("Added $200 to Account B");
                // Commit transaction
                conn.commit();
                System.out.println("\nTransaction committed successfully!");
                stmt.close();
            } catch (SQLException e) {
                System.out.println("\nError occurred! Rolling back transaction...");
                conn.rollback();
                System.out.println("Transaction rolled back!");
            }
            // Display final balances
            System.out.println("\nFinal Balances:");
            displayBalances(conn);
            
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (conn != null) {
                    conn.setAutoCommit(true);
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    
    private static void displayBalances(Connection conn) throws SQLException {
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM accounts");
        while (rs.next()) {
            System.out.println(rs.getString("name") + ": $" + rs.getDouble("balance"));
        }
        rs.close();
        stmt.close();
    }
}