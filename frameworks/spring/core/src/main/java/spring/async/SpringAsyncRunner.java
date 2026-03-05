package spring.async;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicBoolean;

class SpringAsyncRunner {

    static void main(String[] args) throws InterruptedException, ExecutionException {
        var context = new AnnotationConfigApplicationContext(SpringAsyncConfiguration.class);
        var service = context.getBean(AsyncService.class);
        service.doHeavyWork().join();
        service.apiCall().get();
        context.close();
    }

    @EnableAsync
    @Configuration
    static class SpringAsyncConfiguration implements AsyncConfigurer {
        @Override
        public Executor getAsyncExecutor() {
            var threadFactory = new ThreadFactory() {
                @Override
                public Thread newThread(Runnable task) {
                    var thread = new Thread(task);
                    thread.setName("worker-thread-" + thread.threadId());
                    return thread;
                }
            };
            return new ThreadPoolExecutor(
                    1, Runtime.getRuntime().availableProcessors(),
                    5, TimeUnit.MINUTES,
                    new ArrayBlockingQueue<>(100),
                    threadFactory
            );
        }
        @Bean
        public Executor customExecutor() {
            return Executors.newSingleThreadExecutor();
        }
        @Bean
        public AsyncService asyncService() {
            return new AsyncService();
        }
    }

    @Slf4j
    static class AsyncService {
        @Async
        public CompletableFuture<Void> doHeavyWork() throws InterruptedException {
            TimeUnit.MILLISECONDS.sleep(200);
            log.debug("Completed heavy work");
            var future = new CompletableFuture<Void>();
            future.complete(null);
            return future;
        }
        @Async("customExecutor")
        public Future<Void> apiCall() throws InterruptedException {
            var resultReady = new AtomicBoolean();
            TimeUnit.MILLISECONDS.sleep(10);
            resultReady.set(true);
            log.debug("Received API response");
            return new Future<>() {
                @Override
                public boolean cancel(boolean mayInterruptIfRunning) {
                    return false;
                }
                @Override
                public boolean isCancelled() {
                    return false;
                }
                @Override
                public boolean isDone() {
                    return resultReady.get();
                }
                @Override
                public Void get() {
                    return null;
                }
                @Override
                public Void get(long timeout, TimeUnit unit) {
                    return null;
                }
            };
        }
    }

}
