package algorithm.sorting;

import util.Algorithm;
import util.Algorithm.Complexity;

import static data_structure.array.Arrays.swap;
import static util.Algorithm.Complexity.Value.CONSTANT;
import static util.Algorithm.Complexity.Value.POLYNOMIAL;
import static util.Algorithm.Target.IN_PLACE;

class SelectionSort {

    @Algorithm(
        complexity = @Complexity(runtime = POLYNOMIAL, space = CONSTANT),
        target = IN_PLACE
    )
    static int[] sort(int[] arr) {
        for (int i = 0; i < arr.length; i++) {
            int minIndex = i;
            for (int j = i; j < arr.length; j++)
                if (arr[j] < arr[minIndex])
                    minIndex = j;
            swap(arr, i, minIndex);
        }
        return arr;
    }

}
