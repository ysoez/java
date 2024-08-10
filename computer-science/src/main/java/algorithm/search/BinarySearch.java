package algorithm.search;

import util.Algorithm;
import util.Algorithm.Complexity;

import static util.Algorithm.Complexity.Value.*;

class BinarySearch {

    @Algorithm(complexity = @Complexity(runtime = LOGARITHMIC, space = CONSTANT))
    static int searchIterative(int[] arr, int value) {
        var left = 0;
        var right = arr.length - 1;
        while (left <= right) {
            var middle = (left + right) / 2;
            if (arr[middle] == value)
                return middle;
            if (value < arr[middle])
                right = middle - 1;
            else
                left = middle + 1;
        }
        return -1;
    }

    @Algorithm(complexity = @Complexity(runtime = LOGARITHMIC, space = LOGARITHMIC))
    static int searchRecursive(int[] arr, int value) {
        return searchRecursive(arr, value, 0, arr.length - 1);
    }

    static int searchRecursive(int[] arr, int value, int left, int right) {
        if (right < left)
            return -1;
        int middle = (left + right) / 2;
        if (arr[middle] == value)
            return middle;
        if (value < arr[middle])
            return searchRecursive(arr, value, left, middle - 1);
        return searchRecursive(arr, value, middle + 1, right);
    }

}
