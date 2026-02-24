package hikari;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.SQLException;

class HikariDataSourceWrapper {

    public static void main(String[] args) throws SQLException {
        var config = new HikariConfig();
        config.setJdbcUrl("jdbc:h2:mem:basicdb");
        config.setUsername("sa");
        config.setMaximumPoolSize(2);
        try (HikariDataSource ds = new HikariDataSource(config)) {
            System.out.println(ds.getClass().getSimpleName() + " pool initialized successfully");
            for (int i = 0; i < 3; i++) {
                try (Connection conn = ds.getConnection()) {
                    System.out.println("got connection: " + conn);
                }
            }
        }
    }

}