package persistence.util;

import org.hibernate.dialect.Dialect;
import persistence.util.query.Queries;

import javax.sql.DataSource;
import java.util.Properties;

public interface DataSourceProvider {

	String hibernateDialect();

	DataSource dataSource();

	Class<? extends DataSource> dataSourceClassName();

	Properties dataSourceProperties();

	String url();

	String username();

	String password();

	Database database();

	Queries queries();

	default Class<? extends Dialect> hibernateDialectClass() {
		return ReflectionUtils.getClass(hibernateDialect());
	}
}
