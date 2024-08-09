package stream.collector;

import java.util.List;
import java.util.Map;

import static java.util.function.Function.identity;
import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;

class StreamGroupingBy {

    public static void main(String[] args) {
        System.out.println(groupBy(List.of("John", "Sarah", "Mark", "Sarah", "Eric")));
        System.out.println(groupByAndCounting(List.of("John", "Sarah", "Mark", "Sarah", "Eric")));
    }

    private static Map<String, List<String>> groupBy(List<String> names) {
        return names.stream().collect(groupingBy(identity()));
    }

    private static Map<String, Long> groupByAndCounting(List<String> names) {
        return names.stream().collect(groupingBy(identity(), counting()));
    }

}
