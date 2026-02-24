package hikari;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.SQLException;

class HikariConnectionPoolExhaustion {

    @SuppressWarnings("CallToPrintStackTrace")
    public static void main(String[] args) throws SQLException {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl("jdbc:h2:mem:sizedb");
        config.setMaximumPoolSize(1);
        config.setConnectionTimeout(2000);
        try (HikariDataSource ds = new HikariDataSource(config)) {
            Connection conn1 = ds.getConnection();
            System.out.println("first connection acquired");
            try {
                System.out.println("attempting second connection");
                //
                // ~ connection is not available, request timed out
                //
                ds.getConnection();
            } catch (Exception e) {
                e.printStackTrace();
            }
            conn1.close();
        }
    }

}