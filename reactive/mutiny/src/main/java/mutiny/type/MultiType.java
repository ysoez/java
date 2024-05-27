package mutiny.type;

import io.smallrye.mutiny.Multi;

import java.util.stream.IntStream;

class MultiType {

    // ~ multi emits either 0, 1, n items or failure
    public static void main(String[] args) {
        // ~ success
        Multi.createFrom()
                .items(IntStream.rangeClosed(0, 10).boxed())
                .onItem().transform(item -> item * 2)
                .onItem().transform(String::valueOf)
                .select().last(2)
                .subscribe().with(System.out::println);

        // ~ failure with fallback
        Multi.createFrom()
                .items(IntStream.rangeClosed(0, 10).boxed())
                .onItem().transform(item -> item / 0)
                .onItem().transform(String::valueOf)
                .onFailure().recoverWithItem("fallback")
                .select().first(5)
                .subscribe().with(System.out::println);

        // ~ failure
        Multi.createFrom()
                .items(IntStream.rangeClosed(0, 10).boxed())
                .onItem().transform(item -> item / 0)
                .onItem().transform(String::valueOf)
                .onFailure().invoke(err -> System.err.print("Transform failed: " + err))
                .subscribe().with(System.out::println, err -> System.err.print("Pipeline failed: " + err));
    }

}
