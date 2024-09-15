package library.optional;

import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

class OptionalOrElse {

    public static void main(String[] args) {
        var random = ThreadLocalRandom.current().nextInt() % 2 == 0 ? 1 : null;
        System.out.println(Optional.ofNullable(random).orElse(0));
        System.out.println(Optional.ofNullable(random).orElseGet(() -> 0));
        Optional.ofNullable(random).orElseThrow(() -> new RuntimeException("random is null"));
    }

}
