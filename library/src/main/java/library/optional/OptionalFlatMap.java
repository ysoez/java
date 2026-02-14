package library.optional;

import java.util.Arrays;
import java.util.Optional;

class OptionalFlatMap {

    public static void main(String[] args) {
        System.out.println(Optional.of(Arrays.asList("apple", "banana", "cherry")).flatMap(list -> list.stream().findFirst()));
    }

}
