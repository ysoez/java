package mutiny.type;

import io.smallrye.mutiny.Multi;

import java.util.stream.IntStream;

class MultiType {

    public static void main(String[] args) {
        Multi.createFrom()
                .items(IntStream.rangeClosed(0, 10).boxed())
                .onItem().transform(item -> item * 2)
                .onItem().transform(String::valueOf)
                .select().last(2)
                .subscribe().with(System.out::println);
    }

}
