package persistence.datasource.provider;

import lombok.Getter;
import org.testcontainers.containers.JdbcDatabaseContainer;
import org.testcontainers.containers.PostgreSQLContainer;
import persistence.datasource.provider.container.PostgreSqlDataSourceProvider;
import persistence.util.ReflectionUtils;

import java.util.Collections;

public enum Database {

	POSTGRESQL {
		@Override
		public Class<? extends DataSourceProvider> dataSourceProviderClass() {
			return PostgreSqlDataSourceProvider.class;
		}
		@Override
		protected JdbcDatabaseContainer<?> newJdbcDatabaseContainer() {
			return new PostgreSQLContainer<>("postgres:15.3");
		}
	};

	@Getter
	private JdbcDatabaseContainer<?> container;

	public DataSourceProvider dataSourceProvider() {
		return ReflectionUtils.newInstance(dataSourceProviderClass().getName());
	}
	
	public abstract Class<? extends DataSourceProvider> dataSourceProviderClass();

	public void initContainer(String username, String password) {
		container = newJdbcDatabaseContainer()
			.withReuse(true)
			.withEnv(Collections.singletonMap("ACCEPT_EULA", "Y"))
			.withTmpFs(Collections.singletonMap("/testtmpfs", "rw"));
		if(supportsDatabaseName()) {
			container.withDatabaseName(databaseName());
		}
		if(supportsCredentials()) {
			container.withUsername(username).withPassword(password);
		}
		container.start();
	}

	protected JdbcDatabaseContainer<?> newJdbcDatabaseContainer() {
		throw new UnsupportedOperationException(String.format("[%s] database was not configured to use Testcontainers", name()));
	}

	protected boolean supportsDatabaseName() {
		return true;
	}

	protected String databaseName() {
		return "test-db";
	}

	protected boolean supportsCredentials() {
		return true;
	}

}
