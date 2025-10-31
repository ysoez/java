package mutiny.type;

import io.smallrye.mutiny.Multi;

import java.util.stream.IntStream;

class MultiType {

    public static void main(String[] args) {
        //
        // ~ multi emits either 0-n items or failure
        //
        onSuccess();
        onFailure();
        onFailureRecovery();
    }

    private static void onSuccess() {
        Multi.createFrom()
                .items(IntStream.rangeClosed(0, 10).boxed())
                .onItem().transform(item -> item * 2)
                .onItem().transform(String::valueOf)
                .select().last(2)
                .subscribe().with(System.out::println);
    }

    private static void onFailure() {
        Multi.createFrom()
                .items(IntStream.rangeClosed(0, 10).boxed())
                .onItem().transform(item -> item / 0)
                .onItem().transform(String::valueOf)
                .onFailure().invoke(err -> System.err.println("Transform failed: " + err))
                .subscribe().with(System.out::println, err -> System.err.println("Pipeline failed: " + err));
    }

    private static void onFailureRecovery() {
        Multi.createFrom()
                .items(IntStream.rangeClosed(0, 10).boxed())
                .onItem().transform(item -> item / 0)
                .onItem().transform(String::valueOf)
                .onFailure().recoverWithItem("fallback")
                .select().first(5)
                .subscribe().with(System.out::println);
    }

}
