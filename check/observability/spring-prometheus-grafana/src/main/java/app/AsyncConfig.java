package app;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.binder.jvm.ExecutorServiceMetrics;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.concurrent.*;

@Component
@EnableAsync
@RequiredArgsConstructor
public class AsyncConfig implements AsyncConfigurer {

    private final MeterRegistry meterRegistry;

    @Override
    public Executor getAsyncExecutor() {
        ExecutorService threadPool = new ThreadPoolExecutor(
                4,
                10,
                100,
                TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(5),
                threadFactory(),
                (r, executor) -> System.out.println("A task got rejected!")
        );
        return ExecutorServiceMetrics.monitor(meterRegistry, threadPool, "custom-thread-pool", new ArrayList<>());
    }

    private ThreadFactory threadFactory() {
        return r -> {
            Thread t = new Thread(r);
            t.setName("custom-thread");
            return t;
        };
    }
}
