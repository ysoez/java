package algorithm.sorting;

import util.Algorithm;
import util.Algorithm.Complexity;

import static data_structure.array.Arrays.swap;
import static util.Algorithm.Complexity.Value.CONSTANT;
import static util.Algorithm.Complexity.Value.POLYNOMIAL;
import static util.Algorithm.Target.IN_PLACE;

class BubbleSort {

    @Algorithm(
        complexity = @Complexity(runtime = POLYNOMIAL, space = CONSTANT),
        target = IN_PLACE
    )
    static int[] base(int[] arr) {
        for (int i = 0; i < arr.length; i++)
            for (int j = 1; j < arr.length; j++)
                if (arr[j - 1] > arr[j])
                    swap(arr, j, j - 1);
        return arr;
    }

    @Algorithm(
        complexity = @Complexity(runtime = POLYNOMIAL, space = CONSTANT),
        target = IN_PLACE
    )
    static int[] sortCheck(int[] arr) {
        boolean isSorted;
        for (int i = 0; i < arr.length; i++) {
            isSorted = true;
            for (int j = 1; j < arr.length; j++) {
                if (arr[j - 1] > arr[j]) {
                    swap(arr, j, j - 1);
                    isSorted = false;
                }
            }
            if (isSorted)
                return arr;
        }
        return arr;
    }

    @Algorithm(
        complexity = @Complexity(runtime = POLYNOMIAL, space = CONSTANT),
        target = IN_PLACE
    )
    static int[] reducedNestedIterations(int[] arr) {
        boolean isSorted;
        for (int i = 0; i < arr.length; i++) {
            isSorted = true;
            for (int j = 1; j < arr.length - i; j++) {
                if (arr[j - 1] > arr[j]) {
                    swap(arr, j, j - 1);
                    isSorted = false;
                }
            }
            if (isSorted)
                return arr;
        }
        return arr;
    }

}
