package persistence.datasource.provider;

import javax.sql.DataSource;
import java.util.Properties;

public interface DataSourceProvider {

    String hibernateDialect();

    DataSource dataSource();

    Class driverClassName();

    Class<? extends DataSource> dataSourceClassName();

    Properties dataSourceProperties();

    String url();

    String username();

    String password();

    Database database();

}
