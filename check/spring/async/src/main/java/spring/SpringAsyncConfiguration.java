package spring;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;

import java.util.concurrent.*;

@EnableAsync
@Configuration
class SpringAsyncConfiguration implements AsyncConfigurer {

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

}
