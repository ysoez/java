package sandbox.balancer;

import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.util.Collections.emptyList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class LoadBalancerTest {

    @Test
    void testRegisterServers() {
        var lb = LoadBalancer.roundRobin();

        lb.register("server-1");
        lb.register("server-2");

        assertEquals(List.of("server-1", "server-2"), lb.currentServers());
    }

    @Test
    void testUnregisterServers() {
        var lb = LoadBalancer.roundRobin();
        lb.register("server-1");
        lb.register("server-2");

        lb.unregister("server-1");
        assertEquals(List.of("server-2"), lb.currentServers());

        lb.unregister("server-2");
        assertEquals(emptyList(), lb.currentServers());
    }

    @Test
    void testNoAvailableServer() {
        var lb = LoadBalancer.roundRobin();

        assertThrows(NoAvailableServerException.class, lb::nextServer);
    }

    @Test
    void testRoundRobbinHappyPath() {
        var lb = LoadBalancer.roundRobin();

        lb.register("server-1");
        lb.register("server-2");

        assertEquals("server-1", lb.nextServer());
        assertEquals("server-2", lb.nextServer());
        assertEquals("server-1", lb.nextServer());
        assertEquals("server-2", lb.nextServer());
    }

    @Test
    void testRoundRobbinConcurrentMode() throws InterruptedException {
        var lb = LoadBalancer.roundRobin();
        lb.register("server-1");
        lb.register("server-2");
        lb.register("server-3");

        var threadCount = 3;
        var iterations = threadCount * 2;
        var latch = new CountDownLatch(iterations);
        var results = Collections.synchronizedList(new ArrayList<>());
        try (var pool = Executors.newFixedThreadPool(threadCount)) {
            for (int i = 0; i < iterations; i++) {
                pool.submit(() -> {
                    results.add(lb.nextServer());
                    latch.countDown();
                });
            }
        }

        latch.await();

        var countByServer = results.stream().collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
        assertEquals(3, countByServer.size());
        countByServer.forEach((server, count) -> {
            assertEquals(2, count);
        });
    }

}
