package reactor;

import org.reactivestreams.Subscription;
import reactor.core.publisher.BaseSubscriber;
import reactor.core.publisher.Flux;

class ReactorFluxOnBackpressureBuffer {

    public static void main(String[] args) {
        Flux.range(1, 100)
                .onBackpressureBuffer(10)
                .subscribe(new BaseSubscriber<>() {
                    @Override
                    protected void hookOnSubscribe(Subscription subscription) {
                        request(1);
                    }
                    @Override
                    protected void hookOnNext(Integer value) {
                        System.out.println(value);
                        request(1);
                    }
                });
    }

}
