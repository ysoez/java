package reactor;

import reactor.core.publisher.Flux;

class ReactorFluxWindow {

    public static void main(String[] args) {
        Flux.range(1, 10)
                .window(3)
                .flatMap(Flux::collectList)
                .subscribe(System.out::println);
    }

}
