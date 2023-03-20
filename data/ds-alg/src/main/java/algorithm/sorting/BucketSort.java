package algorithm.sorting;

import util.Algorithm;
import util.Algorithm.Complexity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static util.Algorithm.Complexity.Value.LINEAR;
import static util.Algorithm.Target.IN_PLACE;

class BucketSort {

    @Algorithm(
        complexity = @Complexity(runtime = LINEAR, space = LINEAR),
        target = IN_PLACE
    )
    static int[] sort(int[] arr, int bucketsCount) {
        var i = 0;
        for (var bucket : createBuckets(arr, bucketsCount)) {
            Collections.sort(bucket);
            for (var item : bucket)
                arr[i++] = item;
        }
        return arr;
    }

    private static List<List<Integer>> createBuckets(int[] arr, int numberOfBuckets) {
        List<List<Integer>> buckets = new ArrayList<>();
        for (var i = 0; i < numberOfBuckets; i++)
            buckets.add(new ArrayList<>());
        for (var item : arr)
            buckets.get(item / numberOfBuckets).add(item);
        return buckets;
    }

}
