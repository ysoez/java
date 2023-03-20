package algorithm.sorting;

import util.Algorithm;
import util.Algorithm.Complexity;

import static util.Algorithm.Complexity.Value.CONSTANT;
import static util.Algorithm.Complexity.Value.POLYNOMIAL;
import static util.Algorithm.Target.IN_PLACE;

class InsertionSort {

    @Algorithm(
        complexity = @Complexity(runtime = POLYNOMIAL, space = CONSTANT),
        target = IN_PLACE
    )
    static int[] sort(int[] arr) {
        for (int i = 1; i < arr.length; i++) {
            var current = arr[i];
            var j = i - 1;
            while (j >= 0 && arr[j] > current) {
                arr[j + 1] = arr[j];
                j--;
            }
            arr[j + 1] = current;
        }
        return arr;
    }

}

