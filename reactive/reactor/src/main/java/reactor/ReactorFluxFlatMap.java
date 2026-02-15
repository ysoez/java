package reactor;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

class ReactorFluxFlatMap {

    public static void main(String[] args) {
        Flux.just("user1", "user2")
                .flatMap(userId -> Mono.just(userId + "-data"))
                .subscribe(System.out::println);
    }

}
