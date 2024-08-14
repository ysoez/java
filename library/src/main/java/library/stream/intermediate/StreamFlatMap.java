package library.stream.intermediate;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Stream;

class StreamFlatMap {

    public static void main(String[] args) {
        Integer sum = Stream.of(Set.of(1, 2), Set.of(3, 4))
                .flatMap(Collection::stream)
                .reduce(0, Integer::sum);
        System.out.println(sum);
    }

}
