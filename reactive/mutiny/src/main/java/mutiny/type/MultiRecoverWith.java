package mutiny.type;

import io.smallrye.mutiny.Multi;

import java.util.stream.IntStream;

class MultiRecoverWith {

    public static void main(String[] args) {
        Multi.createFrom()
                .items(IntStream.rangeClosed(0, 10).boxed())
                .onItem().transform(item -> item / 0)
                .onItem().transform(String::valueOf)
                .onFailure().recoverWithItem("fallback")
                .select().first(5)
                .subscribe().with(System.out::println);
    }

}
