package library.optional;

import java.util.Optional;

class OptionalMap {

    public static void main(String[] args) {
        System.out.println(Optional.of("hello-world").map(String::length).orElse(0));
    }

}
