package mutiny.type;

import io.smallrye.mutiny.Uni;

public class UniType {

    public static void main(String[] args) {
        //
        // ~ uni emits either 1 item or failure
        //
        onSuccess();
        onFailure();
    }

    private static void onSuccess() {
        Uni.createFrom()
                .item("item")
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
