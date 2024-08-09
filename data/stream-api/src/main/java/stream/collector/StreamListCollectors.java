package stream.collector;

import java.util.stream.Collectors;
import java.util.stream.Stream;

class StreamListCollectors {

    @SuppressWarnings("SimplifyStreamApiCallChains")
    public static void main(String[] args) {
        System.out.println(Stream.of(1, 2, 3).collect(Collectors.toList()).getClass());
        System.out.println(Stream.of(1, 2, 3).collect(Collectors.toUnmodifiableList()).getClass());
        System.out.println(Stream.of(1, 2, 3).toList().getClass());
    }

}
