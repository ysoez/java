package sandbox.balancer;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

class LoadBalancer {

    private final List<Server> servers;
    private final Router router;

    private LoadBalancer(Router router) {
        this.servers = new CopyOnWriteArrayList<>();
        this.router = router;
    }

    static LoadBalancer roundRobin() {
        return new LoadBalancer(new RoundRobinRouter());
    }

    static LoadBalancer leastConnections() {
        return new LoadBalancer(new LeastConnectionsRouter());
    }

    void register(String address) {
        servers.add(new Server(address));
    }

    void unregister(String address) {
        servers.removeIf(server -> server.getAddress().equals(address));
    }

    String nextServer() {
        if (servers.isEmpty()) {
            throw new NoAvailableServerException();
        }
        return router.nextServer(servers);
    }

    List<String> currentServers() {
        return servers.stream().map(Server::getAddress).toList();
    }

}
