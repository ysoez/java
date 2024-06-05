package netty.event;

import java.util.Collections;
import java.util.List;

@SuppressWarnings("CallToPrintStackTrace")
class EventLoop {

    private boolean terminated;

    public void execute() {
        while (!terminated) {
            List<Runnable> readyEvents = blockUntilEventsReady();
            for (Runnable ev : readyEvents) {
                ev.run();
            }
        }
    }

    private List<Runnable> blockUntilEventsReady() {
        return Collections.singletonList(() -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
    }
}
