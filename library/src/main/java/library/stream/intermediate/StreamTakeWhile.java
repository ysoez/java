package library.stream.intermediate;

import java.util.stream.Stream;

class StreamTakeWhile {

    public static void main(String[] args) {
        Stream<Integer> taken = Stream.of(1, 2, 3, 4, 5, 6, 7, 8, 9).takeWhile(n -> n < 5);
        taken.forEach(System.out::println);
    }

}
