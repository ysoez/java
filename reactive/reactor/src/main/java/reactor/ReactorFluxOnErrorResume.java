package reactor;

import reactor.core.publisher.Flux;

class ReactorFluxOnErrorResume {

    public static void main(String[] args) {
        Flux.range(1, 5)
                .map(i -> {
                    if (i == 3) throw new RuntimeException("error at 3");
                    return i;
                })
                .onErrorResume(e -> Flux.just(999))
                .subscribe(System.out::println);
    }

}
