package library.stream.collector;

import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.joining;

class StreamCollectingAndThenCollector {

    public static void main(String[] args) {
        System.out.println(concat(List.of("John", "Sarah", "Mark")));
        System.out.println(concatUppercase(List.of("John", "Sarah", "Mark")));
    }

    private static Optional<String> concat(List<String> names) {
        return names.stream().collect(collectingAndThen(joining(", "), Optional::of));
    }

    private static String concatUppercase(List<String> names) {
        return names.stream().collect(collectingAndThen(joining(", "), String::toUpperCase));
    }

}
