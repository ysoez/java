package persistence.util.spring.flexypool;

import com.vladmihalcea.flexypool.FlexyPoolDataSource;
import com.vladmihalcea.flexypool.adaptor.HikariCPPoolAdapter;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.context.annotation.Configuration;
import persistence.util.spring.HikariCPPostgreSQLJPAConfiguration;

import javax.sql.DataSource;

@Configuration
public class FlexyPoolTestConfiguration extends HikariCPPostgreSQLJPAConfiguration {

    @Override
    protected Class configurationClass() {
        return FlexyPoolEntities.class;
    }

    @Override
    public DataSource actualDataSource() {
        final HikariDataSource dataSource = (HikariDataSource) super.actualDataSource();

        com.vladmihalcea.flexypool.config.Configuration<HikariDataSource> configuration =
                new com.vladmihalcea.flexypool.config.Configuration.Builder<>(
                        "flexy-pool-test",
                        dataSource,
                        HikariCPPoolAdapter.FACTORY
                )
                        .build();
        return new FlexyPoolDataSource<>(
                configuration
        );
    }
}
