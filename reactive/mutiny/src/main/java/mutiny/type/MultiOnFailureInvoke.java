package mutiny.type;

import io.smallrye.mutiny.Multi;

import java.util.stream.IntStream;

class MultiOnFailureInvoke {

    public static void main(String[] args) {
        Multi.createFrom()
                .items(IntStream.rangeClosed(0, 10).boxed())
                .onItem().transform(item -> item / 0)
                .onItem().transform(String::valueOf)
                .onFailure().invoke(err -> System.err.println("transform failed: " + err))
                .subscribe().with(System.out::println, err -> System.err.println("pipeline failed: " + err));
    }

}
