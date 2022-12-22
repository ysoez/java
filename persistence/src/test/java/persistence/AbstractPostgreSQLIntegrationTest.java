package persistence;

import org.hibernate.dialect.PostgreSQLDialect;
import org.postgresql.ds.PGSimpleDataSource;

import javax.sql.DataSource;

public abstract class AbstractPostgreSQLIntegrationTest extends AbstractTest {

    @Override
    public DataSource providerDataSource() {
        var dataSource = new PGSimpleDataSource();
        dataSource.setUrl(POSTGRESQL_CONTAINER.getJdbcUrl());
        dataSource.setUser(POSTGRESQL_CONTAINER.getUsername());
        dataSource.setPassword(POSTGRESQL_CONTAINER.getPassword());
        // dataSource.setReWriteBatchedInserts();
        return dataSource;
    }

    @Override
    protected String hibernateDialect() {
        return PostgreSQLDialect.class.getName();
    }

}
