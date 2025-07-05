package algorithm.sorting;

import util.Algorithm;
import util.Algorithm.Complexity;

import static data_structure.array.Arrays.swap;
import static util.Algorithm.Complexity.Value.LINEAR;
import static util.Algorithm.Complexity.Value.LOGARITHMIC;
import static util.Algorithm.Target.IN_PLACE;

class QuickSort {

    @Algorithm(
        complexity = @Complexity(runtime = LOGARITHMIC, space = LINEAR),
        target = IN_PLACE
    )
    static int[] sort(int[] arr, int start, int end) {
        if (start >= end)
            return arr;
        int boundary = partition(arr, start, end);
        sort(arr, start, boundary - 1);
        sort(arr, boundary + 1, end);
        return arr;
    }

    private static int partition(int[] array, int start, int end) {
        var pivot = array[end];
        var boundary = start - 1;
        for (int i = start; i <= end; i++)
            if (array[i] <= pivot)
                swap(array, i, ++boundary);
        return boundary;
    }

}
