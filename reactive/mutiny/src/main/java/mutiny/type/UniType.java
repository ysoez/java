package mutiny.type;

import io.smallrye.mutiny.Uni;

class UniType {

    public static void main(String[] args) {
        Uni.createFrom()
                .item("item")
                .onItem().transform(String::toUpperCase)
                .subscribe().with(System.out::println);
        Uni.createFrom()
                .item("item")
                .onItem().castTo(Integer.class)
                .subscribe().with(System.out::println, System.err::println);
    }

}
