package persistence.connection;

import com.vladmihalcea.flexypool.FlexyPoolDataSource;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;
import persistence.util.spring.flexypool.FlexyPoolEntities.Post;
import persistence.util.spring.flexypool.FlexyPoolTestConfiguration;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicLong;

@Slf4j
class FlexyPoolConnectionPoolMonitoring implements InitializingBean, DisposableBean {

    @PersistenceContext
    private EntityManager entityManager;
    @Autowired
    private TransactionTemplate transactionTemplate;
    @Autowired
    private FlexyPoolDataSource<DataSource> dataSource;

    private final int seconds = 120;
    private final int threadCount = 6;
    private final ExecutorService executorService = Executors.newFixedThreadPool(threadCount);

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        try (var ctx = new AnnotationConfigApplicationContext(FlexyPoolTestConfiguration.class, FlexyPoolConnectionPoolMonitoring.class)) {
            ctx.getBean(FlexyPoolConnectionPoolMonitoring.class).run();
        }
    }

    @Override
    public void afterPropertiesSet() {
        dataSource.start();
    }

    @Override
    public void destroy() {
        executorService.shutdownNow();
        dataSource.stop();
    }

    private void run() throws InterruptedException {
        long startNanos = System.nanoTime();
        var awaitTermination = new CountDownLatch(threadCount);
        var postCount = new AtomicLong();
        List<Callable<Void>> tasks = new ArrayList<>();
        for (int i = 0; i < threadCount; i++) {
            tasks.add(() -> {
                        log.info("Starting worker thread");
                        while (TimeUnit.NANOSECONDS.toSeconds(System.nanoTime() - startNanos) < seconds) {
                            transactionTemplate.execute((TransactionCallback<Void>) transactionStatus -> {
                                for (int j = 0; j < 100; j++) {
                                    entityManager.persist(new Post());
                                }
                                postCount.set(entityManager.createQuery("select count(p) from Post p", Number.class)
                                        .getSingleResult()
                                        .longValue()
                                );
                                if (postCount.get() % 1000 == 0) {
                                    log.info("Post entity count: {}", postCount);
                                    sleep(250, TimeUnit.MILLISECONDS);
                                }
                                return null;
                            });
                        }
                        awaitTermination.countDown();
                        return null;
                    }
            );
        }
        executorService.invokeAll(tasks);
        awaitTermination.await();
    }

    private void sleep(long duration, TimeUnit timeUnit) {
        try {
            Thread.sleep(timeUnit.toMillis(duration));
        } catch (InterruptedException e) {
            Thread.interrupted();
        }
    }

}
