package micrometer;

import io.micrometer.core.instrument.DistributionSummary;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.simple.SimpleMeterRegistry;

class MicrometerDistributionSummary {

    public static void main(String[] args) {
        MeterRegistry registry = new SimpleMeterRegistry();
        DistributionSummary summary = DistributionSummary.builder("app.payload.size")
                .description("payload size distribution")
                .baseUnit("bytes")
                .register(registry);
        summary.record(512);
        summary.record(1024);
        summary.record(2048);
        System.out.println("count: " + summary.count());
        System.out.println("total amount: " + summary.totalAmount());
        System.out.println("max: " + summary.max());
    }

}
