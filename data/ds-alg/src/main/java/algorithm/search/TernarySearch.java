package algorithm.search;

import util.Algorithm;
import util.Algorithm.Complexity;

import static util.Algorithm.Complexity.Value.CONSTANT;
import static util.Algorithm.Complexity.Value.LOGARITHMIC;

class TernarySearch {

    @Algorithm(complexity = @Complexity(runtime = LOGARITHMIC, space = CONSTANT))
    static int search(int[] arr, int value) {
        return search(arr, value, 0, arr.length - 1);
    }

    private static int search(int[] arr, int value, int left, int right) {
        if (left > right)
            return -1;
        var partitionSize = (right - left) / 3;
        var mid1 = left + partitionSize;
        var mid2 = right - partitionSize;
        if (arr[mid1] == value)
            return mid1;
        if (arr[mid2] == value)
            return mid2;
        if (value < arr[mid1])
            return search(arr, value, left, mid1 - 1);
        if (value > arr[mid2])
            return search(arr, value, mid2 + 1, right);
        return search(arr, value, mid1 + 1, mid2 - 1);
    }

}
