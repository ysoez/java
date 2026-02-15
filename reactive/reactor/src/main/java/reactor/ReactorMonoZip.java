package reactor;

import reactor.core.publisher.Mono;

class ReactorMonoZip {

    public static void main(String[] args) {
        Mono<String> mono1 = Mono.just("a");
        Mono<String> mono2 = Mono.just("b");
        Mono.zip(mono1, mono2).subscribe(tuple -> System.out.println(tuple.getT1() + tuple.getT2()));
    }

}
