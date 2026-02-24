package hikari;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.SQLException;

class HikariConnectionLeakDetection {

    public static void main(String[] args) throws SQLException, InterruptedException {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl("jdbc:h2:mem:leakdb");
        config.setLeakDetectionThreshold(2000);
        try (HikariDataSource ds = new HikariDataSource(config)) {
            Connection leaked = ds.getConnection();
            System.out.println("Connection intentionally not closed...");
            //
            // ~ trigger "Apparent connection leak detected"
            //
            Thread.sleep(3000);
            leaked.close();
        }
    }

}