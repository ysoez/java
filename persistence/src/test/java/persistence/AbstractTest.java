package persistence;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import jakarta.persistence.EntityManagerFactory;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import persistence.util.DataSourceProxyType;

import javax.sql.DataSource;
import java.io.Closeable;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
abstract class AbstractTest extends AbstractHibernateTest {

    private EntityManagerFactory emf;
    private SessionFactory sf;
    private DataSource dataSource;

    private final List<Closeable> closeables = new ArrayList<>();

    @BeforeEach
    public void init() {
        if (nativeHibernateSessionFactoryBootstrap()) {
            sf = newSessionFactory();
        } else {
            emf = newEntityManagerFactory();
        }
        afterInit();
    }

    protected void afterInit() {

    }

    @AfterEach
    public void destroy() {
        if (nativeHibernateSessionFactoryBootstrap()) {
            if (sf != null) {
                sf.close();
            }
        } else {
            if (emf != null) {
                emf.close();
            }
        }
        for (Closeable closeable : closeables) {
            try {
                closeable.close();
            } catch (IOException e) {
                log.error("Failure in destroy callback", e);
            }
        }
        closeables.clear();
    }

    @Override
    protected DataSource dataSource() {
        if (dataSource == null) {
            dataSource = newDataSource();
        }
        return dataSource;
    }

    protected DataSource newDataSource() {
        DataSource dataSource = proxyDataSource()
                ? dataSourceProxyType().dataSource(providerDataSource())
                : providerDataSource();
        if (connectionPooling()) {
            HikariDataSource poolingDataSource = connectionPoolDataSource(dataSource);
            closeables.add(poolingDataSource::close);
            return poolingDataSource;
        } else {
            return dataSource;
        }
    }

    protected boolean proxyDataSource() {
        return true;
    }

    protected DataSourceProxyType dataSourceProxyType() {
        return DataSourceProxyType.DATA_SOURCE_PROXY;
    }

    protected HikariDataSource connectionPoolDataSource(DataSource dataSource) {
        return new HikariDataSource(hikariConfig(dataSource));
    }

    protected HikariConfig hikariConfig(DataSource dataSource) {
        var hikariConfig = new HikariConfig();
        int cpuCores = Runtime.getRuntime().availableProcessors();
        hikariConfig.setMaximumPoolSize(cpuCores * 4);
        hikariConfig.setDataSource(dataSource);
        return hikariConfig;
    }

    protected boolean connectionPooling() {
        return false;
    }

    protected boolean nativeHibernateSessionFactoryBootstrap() {
        return false;
    }

    @Override
    EntityManagerFactory entityManagerFactory() {
        return nativeHibernateSessionFactoryBootstrap() ? sf : emf;
    }

    @Override
    SessionFactory sessionFactory() {
        if (nativeHibernateSessionFactoryBootstrap()) {
            return sf;
        }
        EntityManagerFactory entityManagerFactory = entityManagerFactory();
        if (entityManagerFactory == null) {
            return null;
        }
        return entityManagerFactory.unwrap(SessionFactory.class);
    }

}
