package cluster.election;

public interface ElectionCallback {

    void onLeader();

    void onWorker();

    ElectionCallback NO_OP = new ElectionCallback() {
        @Override
        public void onLeader() {
            // ~ do nothing
        }
        @Override
        public void onWorker() {
            // ~ do nothing
        }
    };

}