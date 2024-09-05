package persistence.util.spring.flyway;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import persistence.util.Database;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@PropertySource({"/META-INF/jdbc-hsqldb.properties"})
public class AbstractHSQLDBFlywayConfiguration extends AbstractFlywayConfiguration {

    @Value("${jdbc.dataSourceClassName}")
    private String dataSourceClassName;

    @Value("${jdbc.url}")
    private String jdbcUrl;

    @Value("${jdbc.username}")
    private String jdbcUser;

    @Value("${jdbc.password}")
    private String jdbcPassword;

    public AbstractHSQLDBFlywayConfiguration() {
        super(Database.HSQLDB);
    }

    @Override
    public DataSource dataSource() {
        Properties driverProperties = new Properties();
        driverProperties.setProperty("url", jdbcUrl);
        driverProperties.setProperty("user", jdbcUser);
        driverProperties.setProperty("password", jdbcPassword);

        Properties properties = new Properties();
        properties.put("dataSourceClassName", dataSourceClassName);
        properties.put("dataSourceProperties", driverProperties);
        //properties.setProperty("minimumPoolSize", String.valueOf(1));
        properties.setProperty("maximumPoolSize", String.valueOf(3));
        properties.setProperty("connectionTimeout", String.valueOf(5000));
        return new HikariDataSource(new HikariConfig(properties));
    }
}
