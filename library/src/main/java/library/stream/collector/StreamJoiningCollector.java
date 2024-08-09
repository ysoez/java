package library.stream.collector;

import java.util.List;

import static java.util.stream.Collectors.joining;

class StreamJoiningCollector {

    public static void main(String[] args) {
        System.out.println(join(List.of("Java", "Stream", "API")));
    }

    @SuppressWarnings("SimplifyStreamApiCallChains")
    private static String join(List<String> words) {
        return words.stream().collect(joining(" "));
    }

}
