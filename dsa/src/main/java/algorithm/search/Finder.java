package algorithm.search;

import data_structure.Algorithm;
import data_structure.Algorithm.Complexity;

import java.util.HashMap;

import static data_structure.Algorithm.Complexity.Value.LINEAR;

class Finder {

    @Algorithm(complexity = @Complexity(runtime = LINEAR, space = LINEAR))
    static <E> E mostFrequent(E[] arr) {
        E value = null;
        var frequencyMap = new HashMap<E, Integer>();
        var mostFrequent = Integer.MIN_VALUE;
        for (var e : arr) {
            var count = frequencyMap.getOrDefault(e, 0) + 1;
            frequencyMap.put(e, count);
            if (count > mostFrequent) {
                mostFrequent = count;
                value = e;
            }
        }
        return value;
    }

}
