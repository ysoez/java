package sandbox.balancer;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

class RoundRobinRouter implements Router {

    private final AtomicInteger currentIndex = new AtomicInteger(0);

    @Override
    public String nextServer(List<Server> servers) {
        while (true) {
            int size = servers.size();
            if (size == 0)
                throw new NoAvailableServerException();
            int index = currentIndex.getAndIncrement();
            try {
                return servers.get(index % size).getAddress();
            } catch (IndexOutOfBoundsException e) {
                // ~ retry if size changed concurrently
            }
        }
    }

}
