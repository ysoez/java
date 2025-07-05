package persistence;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.spi.PersistenceUnitInfo;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Interceptor;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataBuilder;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.SessionFactoryBuilder;
import org.hibernate.boot.model.naming.ImplicitNamingStrategyLegacyJpaImpl;
import org.hibernate.boot.registry.BootstrapServiceRegistry;
import org.hibernate.boot.registry.BootstrapServiceRegistryBuilder;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.boot.spi.MetadataImplementor;
import org.hibernate.cfg.AvailableSettings;
import org.hibernate.integrator.spi.Integrator;
import org.hibernate.jpa.boot.internal.EntityManagerFactoryBuilderImpl;
import org.hibernate.jpa.boot.internal.PersistenceUnitInfoDescriptor;
import org.hibernate.jpa.boot.spi.IntegratorProvider;
import org.hibernate.jpa.boot.spi.TypeContributorList;
import org.hibernate.usertype.UserType;
import persistence.util.DataSourceProvider;
import persistence.util.DataSourceProxyType;
import persistence.util.Database;
import persistence.util.config.PersistenceUnitInfoImpl;
import persistence.util.transaction.JPATransactionVoidCallback;

import javax.sql.DataSource;
import java.io.Closeable;
import java.util.*;

@Slf4j
public abstract class AbstractConfigurator {

    private EntityManagerFactory emf;
    private SessionFactory sf;

    private DataSource dataSource;
    private List<Closeable> closeables = new ArrayList<>();

    public AbstractConfigurator() {
        init();
    }

    //
    // ~ lifecycle
    //

    public void init() {
        beforeInit();
        if (nativeHibernateSessionFactoryBootstrap()) {
            sf = newSessionFactory();
        } else {
            emf = newEntityManagerFactory();
        }
        afterInit();
    }

    protected void beforeInit() {

    }

    protected void afterInit() {

    }

    //
    // ~ bootstrap
    //

    public EntityManagerFactory entityManagerFactory() {
        return nativeHibernateSessionFactoryBootstrap() ? sf : emf;
    }

    protected boolean nativeHibernateSessionFactoryBootstrap() {
        return false;
    }

    private SessionFactory newSessionFactory() {
        final var bsrb = new BootstrapServiceRegistryBuilder().enableAutoClose();
        Integrator integrator = integrator();
        if (integrator != null) {
            bsrb.applyIntegrator(integrator);
        }

        final BootstrapServiceRegistry bsr = bsrb.build();
        final StandardServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder(bsr)
                .applySettings(properties())
                .build();

        final MetadataSources metadataSources = new MetadataSources(serviceRegistry);
        for (Class annotatedClass : entities()) {
            metadataSources.addAnnotatedClass(annotatedClass);
        }

        String[] packages = packages();
        if (packages != null) {
            for (String annotatedPackage : packages) {
                metadataSources.addPackage(annotatedPackage);
            }
        }

        String[] resources = resources();
        if (resources != null) {
            for (String resource : resources) {
                metadataSources.addResource(resource);
            }
        }

        final MetadataBuilder metadataBuilder = metadataSources.getMetadataBuilder()
                .applyImplicitNamingStrategy(ImplicitNamingStrategyLegacyJpaImpl.INSTANCE);

        final List<UserType<?>> additionalTypes = additionalTypes();
        if (additionalTypes != null) {
            additionalTypes.forEach(type -> {
                metadataBuilder.applyTypes((typeContributions, sr) -> typeContributions.contributeType(type));
            });
        }

        additionalMetadata(metadataBuilder);

        MetadataImplementor metadata = (MetadataImplementor) metadataBuilder.build();

        final SessionFactoryBuilder sfb = metadata.getSessionFactoryBuilder();
        Interceptor interceptor = interceptor();
        if (interceptor != null) {
            sfb.applyInterceptor(interceptor);
        }

        return sfb.build();
    }

    protected EntityManagerFactory newEntityManagerFactory() {
        PersistenceUnitInfo persistenceUnitInfo = persistenceUnitInfo(getClass().getSimpleName());
        Map config = properties();
        Interceptor interceptor = interceptor();
        if (interceptor != null) {
            config.put(AvailableSettings.INTERCEPTOR, interceptor);
        }
        Integrator integrator = integrator();
        if (integrator != null) {
            config.put("hibernate.integrator_provider", (IntegratorProvider) () -> Collections.singletonList(integrator));
        }
        List<UserType<?>> additionalTypes = additionalTypes();
        if (additionalTypes != null) {
            config.put("hibernate.type_contributors", (TypeContributorList) () -> Collections.singletonList(
                    (typeContributions, serviceRegistry) -> {
                        additionalTypes.forEach(typeContributions::contributeType);
                    }
            ));
        }
        return new EntityManagerFactoryBuilderImpl(new PersistenceUnitInfoDescriptor(persistenceUnitInfo), config).build();
    }

    protected Integrator integrator() {
        return null;
    }

    protected String[] packages() {
        return null;
    }

    protected String[] resources() {
        return null;
    }

    protected Interceptor interceptor() {
        return null;
    }

    protected void additionalMetadata(MetadataBuilder metadataBuilder) {

    }

    protected List<UserType<?>> additionalTypes() {
        return null;
    }

    protected PersistenceUnitInfoImpl persistenceUnitInfo(String name) {
        PersistenceUnitInfoImpl persistenceUnitInfo = new PersistenceUnitInfoImpl(name, entityClassNames(), properties());
        String[] resources = resources();
        if (resources != null) {
            persistenceUnitInfo.getMappingFileNames().addAll(Arrays.asList(resources));
        }
        return persistenceUnitInfo;
    }

    //
    // ~ configuration
    //

    protected Class<?>[] entities() {
        return new Class[]{};
    }

    protected List<String> entityClassNames() {
        return Arrays.stream(entities()).map(Class::getName).toList();
    }

    protected Properties properties() {
        Properties properties = new Properties();
        // ~ log settings
        properties.put("hibernate.hbm2ddl.auto", "create-drop");
        properties.put("hibernate.dialect", dataSourceProvider().hibernateDialect());
        // ~ data source settings
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

    protected DataSourceProxyType dataSourceProxyType() {
        return DataSourceProxyType.DATA_SOURCE_PROXY;
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
        DataSource dataSource = proxyDataSource()
                ? dataSourceProxyType().dataSource(dataSourceProvider().dataSource())
                : dataSourceProvider().dataSource();
        if (connectionPooling()) {
            HikariDataSource poolingDataSource = connectionPoolDataSource(dataSource);
            closeables.add(poolingDataSource);
            return poolingDataSource;
        } else {
            return dataSource;
        }
    }

    protected DataSourceProvider dataSourceProvider() {
        return database().dataSourceProvider();
    }

    protected boolean proxyDataSource() {
        return true;
    }

    protected HikariDataSource connectionPoolDataSource(DataSource dataSource) {
        return new HikariDataSource(hikariConfig(dataSource));
    }

    //
    // ~ database
    //

    protected Database database() {
        return Database.HSQLDB;
    }

    //
    // ~ connection pool
    //

    protected boolean connectionPooling() {
        return false;
    }

    protected int connectionPoolSize() {
        int cpuCores = Runtime.getRuntime().availableProcessors();
        return cpuCores * 4;
    }

    protected HikariConfig hikariConfig(DataSource dataSource) {
        var hikariConfig = new HikariConfig();
        hikariConfig.setMaximumPoolSize(connectionPoolSize());
        hikariConfig.setDataSource(dataSource);
        return hikariConfig;
    }

    //
    // ~ utils
    //

    protected void doInJPA(JPATransactionVoidCallback function) {
        EntityTransaction tx = null;
        try (EntityManager entityManager = entityManagerFactory().createEntityManager()) {
            function.beforeTransaction();
            tx = entityManager.getTransaction();
            tx.begin();
            function.accept(entityManager);
            if (!tx.getRollbackOnly()) {
                tx.commit();
            } else {
                try {
                    tx.rollback();
                } catch (Exception e) {
                    log.error("Rollback failure", e);
                }
            }
        } catch (Throwable t) {
            if (tx != null && tx.isActive()) {
                try {
                    tx.rollback();
                } catch (Exception e) {
                    log.error("Rollback failure", e);
                }
            }
            throw t;
        } finally {
            function.afterTransaction();
        }
    }

    protected void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

}
