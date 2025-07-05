package persistence.util.provider;

import org.hibernate.dialect.PostgreSQLDialect;
import org.postgresql.ds.PGSimpleDataSource;
import persistence.util.DataSourceProvider;
import persistence.util.Database;
import persistence.util.query.PostgreSQLQueries;
import persistence.util.query.Queries;

import javax.sql.DataSource;
import java.util.Properties;

public class PostgreSQLDataSourceProvider implements DataSourceProvider {

    private Boolean reWriteBatchedInserts;

    public boolean getReWriteBatchedInserts() {
        return reWriteBatchedInserts;
    }

    public PostgreSQLDataSourceProvider setReWriteBatchedInserts(boolean reWriteBatchedInserts) {
        this.reWriteBatchedInserts = reWriteBatchedInserts;
        return this;
    }

    @Override
    public String hibernateDialect() {
        return PostgreSQLDialect.class.getName();
    }

    @Override
    public DataSource dataSource() {
        PGSimpleDataSource dataSource = new PGSimpleDataSource();
        dataSource.setURL(url());
        dataSource.setUser(username());
        dataSource.setPassword(password());
        if (reWriteBatchedInserts != null) {
            dataSource.setReWriteBatchedInserts(reWriteBatchedInserts);
        }

        return dataSource;
    }

    @Override
    public Class<? extends DataSource> dataSourceClassName() {
        return PGSimpleDataSource.class;
    }

    @Override
    public Properties dataSourceProperties() {
        Properties properties = new Properties();
        properties.setProperty("databaseName", "defaultdb");
        properties.setProperty("serverName", "localhost");
        properties.setProperty("user", username());
        properties.setProperty("password", password());
        if (reWriteBatchedInserts != null) {
            properties.setProperty("reWriteBatchedInserts", String.valueOf(reWriteBatchedInserts));
        }
        return properties;
    }

    @Override
    public String url() {
        return "jdbc:postgresql://localhost/defaultdb";
    }

    @Override
    public String username() {
        return "postgres";
    }

    @Override
    public String password() {
        return "admin";
    }

    @Override
    public Database database() {
        return Database.POSTGRESQL;
    }

    @Override
    public Queries queries() {
        return PostgreSQLQueries.INSTANCE;
    }
}
