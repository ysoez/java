package sandbox.balancer;

import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

class LeastConnectionsRouter implements Router {

    @Override
    public String nextServer(List<Server> servers) {
        if (servers.isEmpty()) {
            throw new NoAvailableServerException();
        }
        PriorityQueue<Server> heap = new PriorityQueue<>(Comparator.comparingInt(Server::getConnections));
        heap.addAll(servers);
        //
        // ~ retry until updated atomically
        //
        while (!heap.isEmpty()) {
            Server minServer = heap.poll();
            int currentConnections = minServer.getConnections();
            if (minServer.incrementConnections(currentConnections)) {
                return minServer.getAddress();
            }
        }
        throw new NoAvailableServerException();
    }
}
