package persistence;

import lombok.extern.slf4j.Slf4j;
import org.hibernate.Interceptor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataBuilder;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.SessionFactoryBuilder;
import org.hibernate.boot.model.naming.ImplicitNamingStrategyLegacyJpaImpl;
import org.hibernate.boot.registry.BootstrapServiceRegistry;
import org.hibernate.boot.registry.BootstrapServiceRegistryBuilder;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.boot.spi.MetadataImplementor;
import org.hibernate.integrator.spi.Integrator;
import org.hibernate.usertype.UserType;

import javax.sql.DataSource;
import java.util.List;
import java.util.Properties;
import java.util.function.Consumer;
import java.util.function.Function;

@Slf4j
abstract class AbstractHibernateTest extends AbstractJpaTest {

    protected abstract String hibernateDialect();

    protected abstract DataSource dataSource();

    protected void additionalMetadata(MetadataBuilder metadataBuilder) {
    }

    @Override
    protected Properties properties() {
        var properties = new Properties();
        properties.put("hibernate.hbm2ddl.auto", "create-drop");
        properties.put("hibernate.dialect", hibernateDialect());
        properties.put("hibernate.generate_statistics", Boolean.TRUE.toString());

        //data source settings
        DataSource dataSource = dataSource();
        if (dataSource != null) {
            properties.put("hibernate.connection.datasource", dataSource);
        }
        additionalProperties(properties);
        return properties;
    }

    public abstract SessionFactory sessionFactory();

    SessionFactory newSessionFactory() {
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

        for (Class<?> annotatedClass : entities()) {
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
        var metadata = (MetadataImplementor) metadataBuilder.build();

        final SessionFactoryBuilder sfb = metadata.getSessionFactoryBuilder();
        Interceptor interceptor = interceptor();
        if (interceptor != null) {
            sfb.applyInterceptor(interceptor);
        }

        return sfb.build();
    }

    protected void doInHibernate(HibernateTransactionConsumer callable) {
        Session session = null;
        Transaction txn = null;
        try {
            session = sessionFactory().openSession();
            callable.beforeTransactionCompletion();
            txn = session.beginTransaction();

            callable.accept(session);
            if (!txn.getRollbackOnly()) {
                txn.commit();
            } else {
                try {
                    txn.rollback();
                } catch (Exception e) {
                    log.error("Rollback failure", e);
                }
            }
        } catch (Throwable t) {
            if (txn != null && txn.isActive()) {
                try {
                    txn.rollback();
                } catch (Exception e) {
                    log.error("Rollback failure", e);
                }
            }
            throw t;
        } finally {
            callable.afterTransactionCompletion();
            if (session != null) {
                session.close();
            }
        }
    }

    protected <T> T doInHibernate(HibernateTransactionFunction<T> callable) {
        T result = null;
        Session session = null;
        Transaction txn = null;
        try {
            session = sessionFactory().openSession();
            callable.beforeTransactionCompletion();
            txn = session.beginTransaction();

            result = callable.apply(session);
            if (!txn.getRollbackOnly()) {
                txn.commit();
            } else {
                try {
                    txn.rollback();
                } catch (Exception e) {
                    log.error("Rollback failure", e);
                }
            }
        } catch (Throwable t) {
            if (txn != null && txn.isActive()) {
                try {
                    txn.rollback();
                } catch (Exception e) {
                    log.error("Rollback failure", e);
                }
            }
            throw t;
        } finally {
            callable.afterTransactionCompletion();
            if (session != null) {
                session.close();
            }
        }
        return result;
    }

    @FunctionalInterface
    public interface HibernateTransactionConsumer extends Consumer<Session> {
        default void beforeTransactionCompletion() {

        }

        default void afterTransactionCompletion() {

        }
    }

    @FunctionalInterface
    public interface HibernateTransactionFunction<T> extends Function<Session, T> {
        default void beforeTransactionCompletion() {

        }

        default void afterTransactionCompletion() {

        }
    }

}
