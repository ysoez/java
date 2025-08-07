package dsa.array;

import dsa.Algorithm;
import dsa.Algorithm.Complexity;

import static dsa.Algorithm.Assumption.ORDERING;
import static dsa.Algorithm.Complexity.Value.CONSTANT;
import static dsa.Algorithm.Complexity.Value.LINEAR;

public class Arrays {

    public static <E> E[] newArray(int capacity) {
        checkCapacity(capacity);
        @SuppressWarnings("unchecked") var arr = (E[]) new Object[capacity];
        return arr;
    }

    public static void checkCapacity(int capacity) {
        if (capacity < 0)
            throw new NegativeArraySizeException();
    }

    public static void checkIndex(int index, int size) {
        if (index < 0 || index >= size) {
            throw new ArrayIndexOutOfBoundsException(index);
        }
    }

    public static void swap(int[] arr, int idx1, int idx2) {
        var tmp = arr[idx1];
        arr[idx1] = arr[idx2];
        arr[idx2] = tmp;
    }

    public static boolean isSorted(int[] arr) {
        if (arr == null || arr.length <= 1)
            return true;
        for (int i = 1; i < arr.length; i++)
            if (arr[i] < arr[i - 1])
                return false;
        return true;
    }

    @Algorithm(assumptions = {ORDERING}, complexity = @Complexity(runtime = LINEAR, space = CONSTANT))
    public static int removeDuplicates(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }
        int uniquePos = 1;
        for (int i = 1; i < nums.length; i++) {
            if (nums[i] != nums[i - 1]) {
                nums[uniquePos] = nums[i];
                uniquePos++;
            }
        }
        return uniquePos;
    }

}
