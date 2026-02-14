package guava.bus;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;

import java.util.function.Consumer;

class GuavaEventBus {

    public static void main(String[] args) {
        var eventBus = new EventBus();
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
        System.out.println("application exited");
    }

}
