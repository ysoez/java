package micrometer;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.simple.SimpleMeterRegistry;

class MicrometerCounter {

    public static void main(String[] args) {
        MeterRegistry registry = new SimpleMeterRegistry();
        Counter counter = Counter.builder("app.requests.total")
                .description("total number of processed requests")
                .tag("endpoint", "/api/users")
                .register(registry);
        counter.increment();
        counter.increment(3);
        System.out.println("counter value: " + counter.count());
    }

}
