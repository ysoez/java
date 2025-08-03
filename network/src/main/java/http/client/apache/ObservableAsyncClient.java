package http.client.apache;

import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.binder.MeterBinder;
import io.micrometer.core.instrument.simple.SimpleMeterRegistry;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.concurrent.FutureCallback;
import org.apache.http.conn.routing.HttpRoute;
import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;
import org.apache.http.impl.nio.client.HttpAsyncClients;
import org.apache.http.impl.nio.conn.PoolingNHttpClientConnectionManager;
import org.apache.http.impl.nio.reactor.DefaultConnectingIOReactor;
import org.apache.http.nio.reactor.IOReactorException;
import org.apache.http.pool.ConnPoolControl;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

class ObservableAsyncClient {

    public static void main(String[] args) throws Exception {
        var requestCount = 10;
        var meterRegistry = new SimpleMeterRegistry();
        var connectionManager = createConnectionManager();
        bindMetrics(meterRegistry, connectionManager);
        try (CloseableHttpAsyncClient client = HttpAsyncClients.custom().setConnectionManager(connectionManager).build();
             ScheduledExecutorService metricsLogger = Executors.newSingleThreadScheduledExecutor()) {
            client.start();
            metricsLogger.scheduleAtFixedRate(new PrintMetrics(meterRegistry), 0, 500, TimeUnit.MILLISECONDS);
            var latch = new CountDownLatch(requestCount);
            sendRequestsAsync(requestCount, client, latch);
            System.out.println("waiting for requests to complete");
            latch.await();
            System.out.println("all requests has completed");
        }
    }

    private static void sendRequestsAsync(int requestCount, CloseableHttpAsyncClient client, CountDownLatch latch) {
        for (int i = 0; i < requestCount; i++) {
            var request = new HttpGet("https://httpbin.org/get");
            client.execute(request, new FutureCallback<>() {
                @Override
                public void completed(HttpResponse result) {
                    System.out.println("response received: " + result.getStatusLine());
                    latch.countDown();
                }
                @Override
                public void failed(Exception ex) {
                    System.out.println("failed: " + ex.getMessage());
                    latch.countDown();
                }
                @Override
                public void cancelled() {
                    System.out.println("cancelled");
                    latch.countDown();
                }
            });
        }
    }

    private static PoolingNHttpClientConnectionManager createConnectionManager() throws IOReactorException {
        var ioReactor = new DefaultConnectingIOReactor();
        var connManager = new PoolingNHttpClientConnectionManager(ioReactor);
        connManager.setMaxTotal(100);
        connManager.setDefaultMaxPerRoute(20);
        return connManager;
    }

    private static void bindMetrics(SimpleMeterRegistry meterRegistry, PoolingNHttpClientConnectionManager connectionManager) {
        new ConnectionPoolMeterBinder(connectionManager).bindTo(meterRegistry);
    }

    private record PrintMetrics(MeterRegistry meterRegistry) implements Runnable {
        @Override
        public void run() {
            System.out.println("----- Micrometer Metrics -----");
            meterRegistry.getMeters().forEach(m -> {
                System.out.println(m.getId().getName() + " = " + meterRegistry.get(m.getId().getName()).gauge().value());
            });
        }
    }

    private record ConnectionPoolMeterBinder(ConnPoolControl<HttpRoute> poolManager) implements MeterBinder {
        @Override
        public void bindTo(MeterRegistry registry) {
            Gauge.builder("httpclient.pool.leased", poolManager, cm -> cm.getTotalStats().getLeased())
                    .register(registry);
            Gauge.builder("httpclient.pool.available", poolManager, cm -> cm.getTotalStats().getAvailable())
                    .register(registry);
            Gauge.builder("httpclient.pool.pending", poolManager, cm -> cm.getTotalStats().getPending())
                    .register(registry);
            Gauge.builder("httpclient.pool.max", poolManager, cm -> cm.getTotalStats().getMax())
                    .register(registry);
        }
    }
}

