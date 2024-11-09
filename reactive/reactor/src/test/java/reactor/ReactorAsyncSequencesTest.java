package reactor;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static java.util.Arrays.asList;

class ReactorAsyncSequencesTest {

    @Test
    void fluxFactoriesTest() {
        Flux<String> empty = Flux.empty();
        StepVerifier.create(empty).expectNextCount(0).verifyComplete();

        Flux<String> seq1 = Flux.just("foo", "bar", "foobar");
        StepVerifier.create(seq1).expectNextCount(3).verifyComplete();

        Flux<String> seq2 = Flux.fromIterable(asList("foo", "bar", "foobar"));
        StepVerifier.create(seq2).expectNextCount(3).verifyComplete();

        Flux<Integer> range = Flux.range(5, 3);
        StepVerifier.create(range).expectNext(5, 6, 7).verifyComplete();
    }

    @Test
    void monoFactoriesTest() {
        Mono<String> empty = Mono.empty();
        StepVerifier.create(empty).expectNextCount(0).verifyComplete();

        Mono<String> value = Mono.just("foo");
        StepVerifier.create(value).expectNext("foo").verifyComplete();
    }

}
