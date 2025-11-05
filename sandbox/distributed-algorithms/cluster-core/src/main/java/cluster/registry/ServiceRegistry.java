package cluster.registry;

import java.util.List;
import java.util.Optional;

public interface ServiceRegistry {

    void register(String address) throws Exception;

    void unregister();

    List<String> getServices() throws Exception;

    Optional<String> getRandomService() throws Exception;

    void subscribeForUpdates();

}
