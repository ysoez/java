package guava.bus;

import com.google.common.eventbus.AsyncEventBus;
import com.google.common.eventbus.Subscribe;

import java.util.concurrent.Executors;
import java.util.function.Consumer;

class GuavaAsyncEventBus {

    public static void main(String[] args) {
        try (var executor = Executors.newFixedThreadPool(2)) {
            var eventBus = new AsyncEventBus(executor);
            eventBus.register(new Consumer<String>() {
                @Override
                @Subscribe
                public void accept(String event) {
                    System.out.println("sending email to " + event);
                }
            });
            eventBus.register(new Consumer<String>() {
                @Override
                @Subscribe
                public void accept(String event) {
                    System.out.println("auditing: " + event);
                }
            });
            eventBus.post("user");
        }
        System.out.println("application exited");
    }

}
