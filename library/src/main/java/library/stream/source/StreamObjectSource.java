package library.stream.source;

import java.util.stream.Stream;

class StreamObjectSource {

    public static void main(String[] args) {
        Stream<Object> objectStream = Stream.of("a", 1, 1.4f);
        objectStream.forEach(System.out::println);
    }

}
