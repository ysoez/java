package sandbox.balancer;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

class Server {

    private final String address;
    private final AtomicInteger connections;

    Server(String address) {
        this.address = address;
        this.connections = new AtomicInteger(0);
    }

    String getAddress() {
        return address;
    }

    boolean incrementConnections(int expected) {
        return connections.compareAndSet(expected, expected + 1);
    }

    void decrementConnections() {
        connections.decrementAndGet();
    }

    int getConnections() {
        return connections.get();
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Server server = (Server) o;
        return Objects.equals(address, server.address);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(address);
    }
}
