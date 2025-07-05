package algorithm;

import util.Algorithm;
import util.Algorithm.Complexity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static java.util.Comparator.reverseOrder;
import static java.util.function.Function.identity;
import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;
import static util.Algorithm.Complexity.Value.LINEAR;

class Lists {

    /**
     * Partitioning is useful for batching.
     * Check out Lists.partition(java.util.List, int) from Google Guava.
     */
    @Algorithm(complexity = @Complexity(runtime = LINEAR, space = LINEAR))
    static <T> List<List<T>> partition(List<T> list, int batchSize) {
        List<List<T>> batches = new ArrayList<>();
        int i = 0;
        while (i < list.size()) {
            int nextInc = Math.min(list.size() - i, batchSize);
            List<T> batch = list.subList(i, i + nextInc);
            batches.add(batch);
            i = i + nextInc;
        }
        return batches;
    }

    @Algorithm(complexity = @Complexity(runtime = LINEAR, space = LINEAR))
    static List<String> getMostFrequentTags(List<String> tweets) {
        return tweets.stream()
                .flatMap(tweet -> Arrays.stream(tweet.split(" ")))
                .filter(word -> word.startsWith("#"))
                .collect(groupingBy(identity(), counting()))
                .entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue(reverseOrder()))
                .map(Map.Entry::getKey)
                .toList();
    }

}
