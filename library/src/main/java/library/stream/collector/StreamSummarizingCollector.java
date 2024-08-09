package library.stream.collector;

import java.util.IntSummaryStatistics;
import java.util.List;

import static java.util.stream.Collectors.summarizingInt;

class StreamSummarizingCollector {

    public static void main(String[] args) {
        System.out.println(statistics(List.of(1, 2, 3, 4, 5)));
    }

    private static IntSummaryStatistics statistics(List<Integer> numbers) {
        return numbers.stream().collect(summarizingInt(Integer::intValue));
    }

}
