package stream.collector;

import java.util.List;

import static java.util.stream.Collectors.*;

class StreamTeeingCollector {

    public static void main(String[] args) {
        System.out.println(avgOfMinMax(List.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)));
    }

    private static Double avgOfMinMax(List<Integer> numbers) {
        return numbers.stream().collect(teeing(
                minBy(Integer::compare),
                maxBy(Integer::compare),
                (min, max) -> (min.orElse(0) + max.orElse(0)) / 2.0)
        );
    }

}
