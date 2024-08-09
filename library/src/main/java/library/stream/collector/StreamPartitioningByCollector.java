package library.stream.collector;

import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

import static java.util.stream.Collectors.partitioningBy;

class StreamPartitioningByCollector {

    private static final Predicate<Integer> EVEN_NUMBER_PREDICATE = n -> n % 2 == 0;

    public static void main(String[] args) {
        System.out.println(partitionBy(List.of(1, 2, 3, 4, 5, 6, 7)));
    }

    private static Map<Boolean, List<Integer>> partitionBy(List<Integer> numbers) {
        return numbers.stream().collect(partitioningBy(EVEN_NUMBER_PREDICATE));
    }

}
