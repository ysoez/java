package persistence.migration;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.EncodedResource;
import org.springframework.jdbc.datasource.init.ScriptUtils;
import org.springframework.stereotype.Component;
import org.springframework.transaction.TransactionException;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;
import persistence.util.Database;
import persistence.util.spring.PostgreSQLJPAConfiguration;

@Slf4j
@Component
class DropPostgresSchema {

    @PersistenceContext
    private EntityManager entityManager;
    @Autowired
    private TransactionTemplate transactionTemplate;
    @Autowired
    private Database database;

    public static void main(String[] args) {
        try (var ctx = new AnnotationConfigApplicationContext(PostgreSQLJPAConfiguration.class, DropPostgresSchema.class)) {
            ctx.getBean(DropPostgresSchema.class).runMigration();
        }
    }

    private void runMigration() {
        try {
            transactionTemplate.execute((TransactionCallback<Void>) transactionStatus -> {
                Session session = entityManager.unwrap(Session.class);
                session.doWork(connection -> {
                    var resource = new EncodedResource(new ClassPathResource(
                            String.format("flyway/scripts/%1$s/drop/drop.sql", database.name().toLowerCase())
                    ));
                    ScriptUtils.executeSqlScript(connection,
                            resource,
                            true, true,
                            ScriptUtils.DEFAULT_COMMENT_PREFIX,
                            ScriptUtils.DEFAULT_BLOCK_COMMENT_START_DELIMITER,
                            ScriptUtils.DEFAULT_BLOCK_COMMENT_END_DELIMITER,
                            ScriptUtils.DEFAULT_COMMENT_PREFIX);
                });
                return null;
            });
        } catch (TransactionException e) {
            log.error("Failure", e);
        }
    }
}
