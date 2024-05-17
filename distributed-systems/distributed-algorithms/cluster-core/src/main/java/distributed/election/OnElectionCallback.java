package distributed.election;

public interface OnElectionCallback {

    void onLeader();

    void onWorker();

    OnElectionCallback NO_OP = new OnElectionCallback() {
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
