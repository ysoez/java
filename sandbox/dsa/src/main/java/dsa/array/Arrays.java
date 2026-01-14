package dsa.array;

import dsa.Algorithm;
import dsa.Algorithm.Complexity;

import static dsa.Algorithm.Complexity.CONSTANT;

public class Arrays {

    public static final int[] EMPTY_INT_ARR = new int[0];
    public static final int[][] EMPTY_2D_INT_ARR = new int[0][0];

    public static <E> E[] newArray(int capacity) {
        checkCapacity(capacity);
        @SuppressWarnings("unchecked") var arr = (E[]) new Object[capacity];
        return arr;
    }

    @Algorithm(complexity = @Complexity(runtime = CONSTANT, space = CONSTANT))
    public static void checkCapacity(int capacity) {
        if (capacity < 0)
            throw new NegativeArraySizeException();
    }

    @Algorithm(complexity = @Complexity(runtime = CONSTANT, space = CONSTANT))
    public static void swap(int[] arr, int idx1, int idx2) {
        var tmp = arr[idx1];
        arr[idx1] = arr[idx2];
        arr[idx2] = tmp;
    }

    @Algorithm(complexity = @Complexity(runtime = CONSTANT, space = CONSTANT))
    public static void swap(Object[] arr, int idx1, int idx2) {
        var tmp = arr[idx1];
        arr[idx1] = arr[idx2];
        arr[idx2] = tmp;
    }

    @Algorithm(complexity = @Complexity(runtime = CONSTANT, space = CONSTANT))
    public static boolean isEmpty(Object[] arr) {
        return arr == null || arr.length == 0;
    }

    @Algorithm(complexity = @Complexity(runtime = CONSTANT, space = CONSTANT))
    public static boolean isEmpty(int[] arr) {
        return arr == null || arr.length == 0;
    }

    public static boolean isSorted(int[] arr) {
        if (arr == null || arr.length <= 1)
            return true;
        for (int i = 1; i < arr.length; i++)
            if (arr[i] < arr[i - 1])
                return false;
        return true;
    }

}
