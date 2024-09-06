package persistence.util.spring;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import persistence.util.DataSourceProvider;
import persistence.util.Database;

import javax.sql.DataSource;

@Configuration
@PropertySource({"/META-INF/jdbc-postgresql.properties"})
public class HikariCPPostgreSQLJPAConfiguration extends AbstractJPAConfiguration {

    protected HikariCPPostgreSQLJPAConfiguration() {
        super(Database.POSTGRESQL);
    }

    @Bean
    public DataSourceProvider dataSourceProvider() {
        return database().dataSourceProvider();
    }

    @Bean(destroyMethod = "close")
    public DataSource actualDataSource() {
        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setMaximumPoolSize(64);
        hikariConfig.setAutoCommit(false);
        hikariConfig.setDataSource(dataSourceProvider().dataSource());
        return new HikariDataSource(hikariConfig);
    }

    @Override
    protected String databaseType() {
        return "postgresql";
    }
}
