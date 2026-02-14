package micrometer;

import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.simple.SimpleMeterRegistry;

import java.util.concurrent.atomic.AtomicInteger;

class MicrometerGauge {

    public static void main(String[] args) {
        MeterRegistry registry = new SimpleMeterRegistry();
        var queueSize = new AtomicInteger(0);
        Gauge.builder("app.queue.size", queueSize, AtomicInteger::get)
                .description("current queue size")
                .register(registry);
        queueSize.set(5);
        System.out.println("gauge value: " + registry.get("app.queue.size").gauge().value());
        queueSize.set(10);
        System.out.println("gauge value: " + registry.get("app.queue.size").gauge().value());
    }

}
