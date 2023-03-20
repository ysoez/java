package algorithm.search;

import util.Algorithm;
import util.Algorithm.Complexity;

import static util.Algorithm.Complexity.Value.CONSTANT;
import static util.Algorithm.Complexity.Value.LINEAR;

class LinearSearch {

    @Algorithm(complexity = @Complexity(runtime = LINEAR, space = CONSTANT))
    static int search(int[] arr, int value) {
        for (int i = 0; i < arr.length; i++) {
            if (value == arr[i])
                return i;
        }
        return -1;
    }

}
