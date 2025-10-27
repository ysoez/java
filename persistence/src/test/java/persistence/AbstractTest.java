package persistence;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.spi.PersistenceUnitInfo;
import org.hibernate.Interceptor;
import org.hibernate.cfg.AvailableSettings;
import org.hibernate.integrator.spi.Integrator;
import org.hibernate.jpa.boot.internal.EntityManagerFactoryBuilderImpl;
import org.hibernate.jpa.boot.internal.PersistenceUnitInfoDescriptor;
import org.hibernate.jpa.boot.spi.IntegratorProvider;
import org.hibernate.jpa.boot.spi.TypeContributorList;
import org.hibernate.usertype.UserType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import persistence.datasource.provider.DataSourceProvider;
import persistence.datasource.provider.Database;
import persistence.jpa.boot.DefaultPersistenceUnitInfo;
import persistence.jpa.boot.EntityManagerFactoryConfigurator;
import persistence.transaction.JPATransactionVoidFunction;

import javax.sql.DataSource;
import java.io.Closeable;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public abstract class AbstractTest implements EntityManagerFactoryConfigurator {

    protected final Logger log = LoggerFactory.getLogger(getClass());

    private DataSource dataSource;
    private EntityManagerFactory emf;
    private final List<Closeable> closeables = new ArrayList<>();

    @BeforeEach
    void init() {
        beforeInit();
        emf = newEntityManagerFactory();
        afterInit();
    }

    protected void beforeInit() {
    }

    protected void afterInit() {
    }

    @AfterEach
    void destroy() {
        if (emf != null) {
            emf.close();
        }
        for (Closeable closeable : closeables) {
            try {
                closeable.close();
            } catch (IOException e) {
                log.error("failure", e);
            }
        }
        closeables.clear();
        afterDestroy();
    }

    protected void afterDestroy() {
    }

    //
    // ~ entity manager bootstrap
    //

    protected EntityManagerFactory newEntityManagerFactory() {
        PersistenceUnitInfo persistenceUnitInfo = persistenceUnitInfo(getClass().getSimpleName());
        Map configuration = properties();
        Interceptor interceptor = interceptor();
        if (interceptor != null) {
            configuration.put(AvailableSettings.INTERCEPTOR, interceptor);
        }
        Integrator integrator = integrator();
        if (integrator != null) {
            configuration.put("hibernate.integrator_provider", (IntegratorProvider) () -> Collections.singletonList(integrator));
        }
        List<UserType<?>> additionalTypes = additionalTypes();
        if (additionalTypes != null) {
            configuration.put("hibernate.type_contributors",
                    (TypeContributorList) () -> Collections.singletonList(
                            (typeContributions, serviceRegistry) -> {
                                additionalTypes.forEach(typeContributions::contributeType);
                            }
                    ));
        }
        var factoryBuilder = new EntityManagerFactoryBuilderImpl(new PersistenceUnitInfoDescriptor(persistenceUnitInfo), configuration);
        return factoryBuilder.build();
    }

    @Override
    public PersistenceUnitInfo persistenceUnitInfo(String name) {
        var persistenceUnitInfo = new DefaultPersistenceUnitInfo(name, entityClassNames(), properties());
        String[] resources = resources();
        if (resources != null) {
            persistenceUnitInfo.getMappingFileNames().addAll(Arrays.asList(resources));
        }
        return persistenceUnitInfo;
    }

    @Override
    public List<String> entityClassNames() {
        return Arrays.stream(entities()).map(Class::getName).collect(Collectors.toList());
    }

    protected Class<?>[] entities() {
        return new Class[]{};
    }

    protected Properties properties() {
        Properties properties = new Properties();
        properties.put("hibernate.hbm2ddl.auto", "create-drop");
        properties.put("hibernate.dialect", dataSourceProvider().hibernateDialect());
        DataSource dataSource = dataSource();
        if (dataSource != null) {
            properties.put("hibernate.connection.datasource", dataSource);
        }
        properties.put("hibernate.generate_statistics", Boolean.TRUE.toString());
        additionalProperties(properties);
        return properties;
    }

    protected void additionalProperties(Properties properties) {

    }

    protected String[] resources() {
        return null;
    }

    protected Interceptor interceptor() {
        return null;
    }

    protected Integrator integrator() {
        return null;
    }

    protected List<UserType<?>> additionalTypes() {
        return null;
    }

    public EntityManagerFactory entityManagerFactory() {
        return emf;
    }

    //
    // ~ data source
    //

    protected DataSource dataSource() {
        if (dataSource == null) {
            dataSource = newDataSource();
        }
        return dataSource;
    }

    protected DataSource newDataSource() {
        DataSource dataSource = dataSourceProvider().dataSource();
        if (proxyDataSource()) {
//            dataSource = dataSourceProxy(dataSource);
        }
        if (connectionPooling()) {
            HikariDataSource poolingDataSource = connectionPoolDataSource(dataSource);
            closeables.add(poolingDataSource::close);
            return poolingDataSource;
        } else {
            return dataSource;
        }
    }

    protected DataSourceProvider dataSourceProvider() {
        return database().dataSourceProvider();
    }

    protected Database database() {
//        return Database.HSQLDB;
        return null;
    }

    protected boolean proxyDataSource() {
        return true;
    }

    //
    // ~ connection pooling
    //

    protected boolean connectionPooling() {
        return false;
    }

    protected HikariDataSource connectionPoolDataSource(DataSource dataSource) {
        return new HikariDataSource(hikariConfig(dataSource));
    }

    protected HikariConfig hikariConfig(DataSource dataSource) {
        var hikariConfig = new HikariConfig();
        hikariConfig.setMaximumPoolSize(connectionPoolSize());
        hikariConfig.setDataSource(dataSource);
        return hikariConfig;
    }

    protected int connectionPoolSize() {
        int cpuCores = Runtime.getRuntime().availableProcessors();
        return cpuCores * 4;
    }

    //
    // ~ context decorators
    //

    protected void doInJPA(JPATransactionVoidFunction function) {
        EntityManager entityManager = null;
        EntityTransaction txn = null;
        try {
            entityManager = entityManagerFactory().createEntityManager();
            function.beforeTransactionCompletion();
            txn = entityManager.getTransaction();
            txn.begin();
            function.accept(entityManager);
            if (!txn.getRollbackOnly()) {
                txn.commit();
            } else {
                try {
                    txn.rollback();
                } catch (Exception e) {
                    log.error("rollback failure", e);
                }
            }
        } catch (Throwable t) {
            if (txn != null && txn.isActive()) {
                try {
                    txn.rollback();
                } catch (Exception e) {
                    log.error("rollback failure", e);
                }
            }
            throw t;
        } finally {
            function.afterTransactionCompletion();
            if (entityManager != null) {
                entityManager.close();
            }
        }
    }

}
