package algorithm.sorting;

import util.Algorithm;
import util.Algorithm.Complexity;

import static util.Algorithm.Complexity.Value.LINEAR;
import static util.Algorithm.Target.IN_PLACE;

class CountingSort {

    @Algorithm(
        complexity = @Complexity(runtime = LINEAR, space = LINEAR),
        target = IN_PLACE
    )
    static int[] sort(int[] arr, int max) {
        int[] counts = new int[max + 1];
        for (var item : arr)
            counts[item]++;
        var k = 0;
        for (var i = 0; i < counts.length; i++)
            for (var j = 0; j < counts[i]; j++)
                arr[k++] = i;
        return arr;
    }

}
