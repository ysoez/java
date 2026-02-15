package reactor;

import reactor.core.publisher.Flux;

class ReactorFluxFilter {

    public static void main(String[] args) {
        Flux.range(1, 10)
                .filter(i -> i % 2 == 0)
                .subscribe(System.out::println);
    }

}
