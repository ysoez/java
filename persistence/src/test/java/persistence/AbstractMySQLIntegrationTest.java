package persistence;

import com.mysql.cj.jdbc.MysqlDataSource;
import org.hibernate.dialect.MySQLDialect;

import javax.sql.DataSource;

public abstract class AbstractMySQLIntegrationTest extends AbstractTest {

    @Override
    public DataSource providerDataSource() {
        var dataSource = new MysqlDataSource();
        dataSource.setUrl(MYSQL_CONTAINER.getJdbcUrl());
        dataSource.setUser(MYSQL_CONTAINER.getUsername());
        dataSource.setPassword(MYSQL_CONTAINER.getPassword());
        return dataSource;
    }

    @Override
    protected String hibernateDialect() {
        return MySQLDialect.class.getName();
    }
}
