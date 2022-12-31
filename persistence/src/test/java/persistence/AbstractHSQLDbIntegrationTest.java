package persistence;

import org.hibernate.dialect.HSQLDialect;
import org.hsqldb.jdbc.JDBCDataSource;

import javax.sql.DataSource;

public abstract class AbstractHSQLDbIntegrationTest extends AbstractTest {

    @Override
    public DataSource providerDataSource() {
        var dataSource = new JDBCDataSource();
        dataSource.setUrl("jdbc:hsqldb:mem:test");
        dataSource.setUser("sa");
        dataSource.setPassword("");
        return dataSource;
    }

    @Override
    protected String hibernateDialect() {
        return HSQLDialect.class.getName();
    }
}
