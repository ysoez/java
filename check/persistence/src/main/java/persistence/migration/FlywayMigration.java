package persistence.migration;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.TransactionException;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;
import persistence.util.spring.PostgreSQLJPAConfiguration;
import persistence.util.spring.flyway.FlywayEntities.Post;

@Slf4j
class FlywayMigration {

    @PersistenceContext
    private EntityManager entityManager;
    @Autowired
    private TransactionTemplate transactionTemplate;
    @Autowired
    private LocalContainerEntityManagerFactoryBean localContainerEntityManagerFactoryBean;

    public static void main(String[] args) {
        try (var ctx = new AnnotationConfigApplicationContext(PostgreSQLJPAConfiguration.class, FlywayMigration.class)) {
            ctx.getBean(FlywayMigration.class).migrate();
        }
    }

    private void migrate() {
        try {
            transactionTemplate.execute((TransactionCallback<Void>) transactionStatus -> {
                Post post = new Post();
                entityManager.persist(post);
                return null;
            });
        } catch (TransactionException e) {
            log.error("Failure", e);
        }
    }

}
