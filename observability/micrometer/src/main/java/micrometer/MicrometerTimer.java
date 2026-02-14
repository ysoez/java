package micrometer;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import io.micrometer.core.instrument.simple.SimpleMeterRegistry;

import java.util.concurrent.TimeUnit;

class MicrometerTimer {

    public static void main(String[] args) {
        MeterRegistry registry = new SimpleMeterRegistry();
        Timer timer = Timer.builder("app.operation.duration")
                .description("time taken for business operation")
                .register(registry);
        timer.record(() -> {
            try {
                Thread.sleep(200);
            } catch (InterruptedException ignored) {
            }
        });
        System.out.println("count: " + timer.count());
        System.out.println("total time (ms): " + timer.totalTime(TimeUnit.MILLISECONDS));
        System.out.println("max time (ms): " + timer.max(TimeUnit.MILLISECONDS));
    }

}
