package mutiny.type;

import io.smallrye.mutiny.Uni;

public class UniType {

    // ~ uni emits either 1 item or failure
    public static void main(String[] args) {
        onSuccess();
        onFailure();
    }

    private static void onSuccess() {
        Uni.createFrom()
                .item("item")
                .onItem().transform(item -> item + "-transform")
                .onItem().transform(String::toUpperCase)
                .subscribe().with(System.out::println);
    }

    private static void onFailure() {
        Uni.createFrom()
                .item("item")
                .onItem().castTo(Integer.class)
                .subscribe().with(System.out::println, System.err::println);
    }

}
