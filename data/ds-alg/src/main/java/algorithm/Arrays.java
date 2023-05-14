package algorithm;

import util.Algorithm;
import util.Algorithm.Complexity;

import java.util.HashMap;

import static util.Algorithm.Complexity.Value.LINEAR;

class Arrays {

    static int[] find2ThatMultiplyFor(int[] arr, int target) {
        return null;
    }

    static int[] find3ThatMultiplyFor(int[] arr, int target) {
        return null;
    }

    @Algorithm(complexity = @Complexity(runtime = LINEAR, space = LINEAR))
    static Integer mostFrequent(int[] arr) {
        Integer count = 0;
        Integer mostFrequent = null;
        var frequency = new HashMap<Integer, Integer>();
        for (int i : arr) {
            frequency.put(i, frequency.getOrDefault(i, 0) + 1);
            if (frequency.get(i) > count) {
                count = frequency.get(i);
                mostFrequent = i;
            }
        }
        return mostFrequent;
    }

}
