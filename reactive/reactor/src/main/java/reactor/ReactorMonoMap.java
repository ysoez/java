package reactor;

import reactor.core.publisher.Mono;

class ReactorMonoMap {

    public static void main(String[] args) {
        Mono.just("hello")
                .map(String::toUpperCase)
                .subscribe(System.out::println);
    }

}
