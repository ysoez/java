package sandbox.balancer;

import java.util.List;

interface Router {

    String nextServer(List<Server> servers);

}
