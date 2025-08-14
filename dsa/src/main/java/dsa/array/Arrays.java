package dsa.array;

import dsa.Algorithm;
import dsa.Algorithm.Complexity;

import java.util.HashMap;

import static dsa.Algorithm.Assumption.ORDERING;
import static dsa.Algorithm.Complexity.Value.*;

public class Arrays {

    static final int[] EMPTY_INT_ARR = new int[0];

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

    interface TwoSum {

        int[] getIndices(int[] arr, int target);

        class BruteForce implements TwoSum {
            @Override
            @Algorithm(complexity = @Complexity(runtime = QUADRATIC, space = CONSTANT))
            public int[] getIndices(int[] arr, int targetSum) {
                for (int i = 0; i < arr.length; i++) {
                    int ntf = targetSum - arr[i];
                    for (int j = i + 1; j < arr.length; j++)
                        if (arr[j] == ntf)
                            return new int[]{i, j};
                }
                return EMPTY_INT_ARR;
            }
            @Override
            public String toString() {
                return getClass().getSimpleName();
            }
        }

        // we test against inx from unsorted array
        // sort - change indexs - broken test
        // works when return numbers but not work get return indeces (sorting)....
//        class XXXX implements TwoSum {
//            @Override
//            @Algorithm(complexity = @Complexity(runtime = LINEARITHMIC, space = CONSTANT))
//            public int[] getIndices(int[] arr, int targetSum) {
//                sort(arr);
//                var left = 0;
//                var right = arr.length - 1;
//                while (left < right) {
//                    var currentSum = arr[left] + arr[right];
//                    if (currentSum == targetSum) {
//                        return new int[]{left, right};
//                    } else if (currentSum < targetSum) {
//                        left++;
//                    } else {
//                        right--;
//                    }
//                }
//                return EMPTY_INT_ARR;
//            }
//        }

        class HashMapOptimized implements TwoSum {
            @Override
            @Algorithm(complexity = @Complexity(runtime = LINEAR, space = LINEAR))
            public int[] getIndices(int[] arr, int targetSum) {
                var numberToIndex = new HashMap<Integer, Integer>();
                for (int i = 0; i < arr.length; i++) {
                    var val = targetSum - arr[i];
                    if (numberToIndex.containsKey(val))
                        return new int[]{numberToIndex.get(val), i};
                    numberToIndex.put(arr[i], i);
                }
                return EMPTY_INT_ARR;
            }
            @Override
            public String toString() {
                return getClass().getSimpleName();
            }
        }
    }

    interface ValidSubsequence {

        boolean validate(int[] arr, int[] sequence);

        class WhileLoop implements ValidSubsequence {
            @Override
            @Algorithm(complexity = @Complexity(runtime = LINEAR, space = CONSTANT))
            public boolean validate(int[] arr, int[] sequence) {
                var arrIdx = 0;
                var seqIdx = 0;
                while (arrIdx < arr.length && seqIdx < sequence.length) {
                    if (arr[arrIdx] == sequence[seqIdx])
                        seqIdx++;
                    arrIdx++;
                }
                return seqIdx == sequence.length;
            }
        }

        class ForLoop implements ValidSubsequence {
            @Override
            @Algorithm(complexity = @Complexity(runtime = LINEAR, space = CONSTANT))
            public boolean validate(int[] arr, int[] sequence) {
                var seqIdx = 0;
                for (int num : arr) {
                    if (seqIdx == sequence.length)
                        break;
                    if (num == sequence[seqIdx])
                        seqIdx++;
                }
                return seqIdx == sequence.length;
            }
        }
    }

}
