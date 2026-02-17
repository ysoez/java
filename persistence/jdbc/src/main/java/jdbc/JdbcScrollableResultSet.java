package jdbc;

import java.sql.*;

public class JdbcScrollableResultSet {

    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/mydb";
        String user = "root";
        String password = "password";

        try (Connection conn = DriverManager.getConnection(url, user, password)) {

            // Create scrollable ResultSet
            Statement stmt = conn.createStatement(
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY
            );

            ResultSet rs = stmt.executeQuery("SELECT * FROM users");

            // Move to last row to get row count
            if (rs.last()) {
                int rowCount = rs.getRow();
                System.out.println("Total rows: " + rowCount);
                System.out.println("=".repeat(80));
            }

            // Navigate through ResultSet
            System.out.println("\nFirst row:");
            rs.first();
            displayCurrentRow(rs);

            System.out.println("\nLast row:");
            rs.last();
            displayCurrentRow(rs);

            System.out.println("\nThird row (absolute positioning):");
            if (rs.absolute(3)) {
                displayCurrentRow(rs);
            } else {
                System.out.println("Row 3 doesn't exist");
            }

            System.out.println("\nNavigating backward from current position:");
            if (rs.relative(-1)) {
                displayCurrentRow(rs);
            }

            System.out.println("\nAll rows in reverse:");
            System.out.println("=".repeat(80));
            rs.afterLast();
            while (rs.previous()) {
                System.out.println("Row " + rs.getRow() + ": " +
                        rs.getInt("id") + ", " +
                        rs.getString("name") + ", " +
                        rs.getString("email"));
            }

            rs.close();
            stmt.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void displayCurrentRow(ResultSet rs) throws SQLException {
        System.out.println("Row " + rs.getRow() + ": " +
                "ID=" + rs.getInt("id") +
                ", Name=" + rs.getString("name") +
                ", Email=" + rs.getString("email"));
    }

}