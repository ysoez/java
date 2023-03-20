package algorithm.sorting;

import util.Algorithm;
import util.Algorithm.Complexity;

import static util.Algorithm.Complexity.Value.LINEAR;
import static util.Algorithm.Complexity.Value.LOGARITHMIC;
import static util.Algorithm.Target.OUT_OF_PLACE;

class MergeSort {

    @SuppressWarnings("ManualArrayCopy")
    @Algorithm(
        complexity = @Complexity(runtime = LOGARITHMIC, space = LINEAR),
        target = OUT_OF_PLACE
    )
    static int[] sort(int[] arr) {
        if (arr.length < 2)
            return arr;

        int middle = arr.length / 2;
        int[] left = new int[middle];
        for(var i = 0; i < middle; i++)
            left[i] = arr[i];
        int[] right = new int[arr.length - middle];
        for(var i = middle; i < arr.length; i++)
            right[i - middle] = arr[i];

        sort(left);
        sort(right);

        merge(left, right, arr);

        return arr;
    }

    private static void merge(int[] left, int[] right, int[] result) {
        int i = 0, j = 0, k = 0;
        while (i < left.length && j < right.length)
            if (left[i] <= right[j])
                result[k++] = left[i++];
            else
                result[k++] = right[j++];
        while (i < left.length)
            result[k++] = left[i++];
        while (j < right.length)
            result[k++] = right[j++];
    }

}
