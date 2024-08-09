package stream.source;

import java.util.List;
import java.util.stream.Stream;

class StreamCollectionSource {

    @SuppressWarnings("SimplifyStreamApiCallChains")
    public static void main(String[] args) {
        Stream<Integer> stream = List.of(1, 2, 3).stream();
        System.out.println(stream);
    }

}
