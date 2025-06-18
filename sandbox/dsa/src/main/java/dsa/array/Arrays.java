package dsa.array;

import dsa.Algorithm;
import dsa.Algorithm.Complexity;

import java.util.HashMap;

import static dsa.Algorithm.Complexity.Value.*;

class Arrays {

    static <E> E[] newArray(int capacity) {
        checkCapacity(capacity);
        @SuppressWarnings("unchecked") var arr = (E[]) new Object[capacity];
        return arr;
    }

    static void checkCapacity(int capacity) {
        if (capacity < 0) {
            throw new NegativeArraySizeException();
        }
    }

    static void checkIndex(int index, int size) {
        if (index < 0 || index >= size) {
            throw new ArrayIndexOutOfBoundsException(index);
        }
    }

    public static void swap(int[] array, int first, int second) {
        var temp = array[first];
        array[first] = array[second];
        array[second] = temp;
    }

    static boolean isEmpty(int[] arr) {
        return arr == null || arr.length == 0;
    }

    // target not found: what to return (null, empty array, 1 value)
    // multiple results: find first or input contain only 1 solution?
    interface TwoSum {

        int[] getIndices(int[] arr, int target);

        // multiple pointers???
        class BruteForce implements TwoSum {
            @Override
            @Algorithm(complexity = @Complexity(runtime = QUADRATIC, space = CONSTANT))
            public int[] getIndices(int[] arr, int target) {
                for (int i = 0; i < arr.length; i++) {
                    int ntf = target - arr[i];
                    for (int j = i + 1; j < arr.length; j++) {
                        if (arr[j] == ntf) {
                            return new int[]{i, j};
                        }
                    }
                }
                return null;
            }
        }

        // trade space for runtime
        class HashMapOptimized implements TwoSum {
            @Override
            @Algorithm(complexity = @Complexity(runtime = LINEAR, space = LINEAR))
            public int[] getIndices(int[] arr, int target) {
                var map = new HashMap<Integer, Integer>();
                for (int i = 0; i < arr.length; i++) {
                    var complement = map.get(target - i);
                    if (complement != null)
                        return new int[]{i, complement};
                    map.put(arr[i], i);
                }
                return null;
            }
        }
    }

    interface MaxWaterContainer {

        MaxWaterContainer BRUTE_FORCE = new MaxWaterContainer.BruteForce();
        MaxWaterContainer SHIFTING_POINTERS = new MaxWaterContainer.ShiftingPointers();

        int getArea(int[] heights);

        class BruteForce implements MaxWaterContainer {
            @Override
            @Algorithm(complexity = @Complexity(runtime = QUADRATIC, space = CONSTANT))
            public int getArea(int[] heights) {
                int maxArea = 0;
                for (int i = 0; i < heights.length; i++) {
                    for (int j = i + 1; j < heights.length; j++) {
                        int height = Math.min(heights[i], heights[j]);
                        int width = j - i;
                        int area = height * width;
                        if (area > maxArea) {
                            maxArea = area;
                        }
                    }
                }
                return maxArea;
            }
        }

        class ShiftingPointers implements MaxWaterContainer {
            @Override
            @Algorithm(complexity = @Complexity(runtime = LINEAR, space = CONSTANT))
            public int getArea(int[] heights) {
                int i = 0, j = heights.length - 1, maxArea = 0;
                while (i < j) {
                    int height = Math.min(heights[i], heights[j]);
                    int width = j - i;
                    int area = height * width;
                    if (area > maxArea) {
                        maxArea = area;
                    }
                    if (heights[i] <= heights[j]) {
                        i++;
                    } else {
                        j--;
                    }
                }
                return maxArea;
            }
        }
    }

//    interface MergeSorted {
//
//        MergeSorted BASIC = new Basic();
//
//        int[] getMerged(int[] arr1, int[] arr2);
//
//        class Basic implements MergeSorted {
//            @Override
//            @Algorithm(complexity = @Complexity(runtime = LINEAR, space = LINEAR))
//            public int[] getMerged(int[] arr1, int[] arr2) {
//                if (arr1 == null && arr2 == null)
//                    return new int[0];
//                if (isEmpty(arr1))
//                    return arr2;
//                if (isEmpty(arr2))
//                    return arr1;
//                int l1 = arr1.length;
//                int l2 = arr2.length;
//                var merged = new int[l1 + l2];
//                int i = 0, a1 = 0, a2 = 0;
//                while (a1 < l1 && a2 < l2) {
//                    if (arr1[a1] <= arr2[a2]) {
//                        merged[i++] = arr1[a1++];
//                    } else {
//                        merged[i++] = arr2[a2++];
//                    }
//                }
//                while (a1 < l1) {
//                    merged[i++] = arr1[a1++];
//                }
//                while (a2 < l2) {
//                    merged[i++] = arr2[a2++];
//                }
//                return merged;
//            }
//        }
//    }



//    @Algorithm(complexity = @Complexity(runtime = LINEAR, space = CONSTANT))
//    public static Integer max(int[] array) {
//        if (array == null)
//            throw new IllegalArgumentException();
//        if (array.length == 0)
//            throw new EmptyArrayException();
//        int max = array[0];
//        for (int i = 1; i < array.length; i++)
//            if (array[i] > max)
//                max = array[i];
//        return max;
//    }
//
//    @Algorithm(complexity = @Complexity(runtime = LINEAR, space = LINEAR), target = OUT_OF_PLACE)
//    static int[] intersectionForSorted(int[] arr1, int[] arr2) {
//        if (arr1 == null || arr2 == null)
//            throw new IllegalArgumentException();
//        var resultSet = new HashSet<Integer>();
//        for (int i = 0, j = 0; i < arr1.length && j < arr2.length; ) {
//            if (arr1[i] < arr2[j]) {
//                i++;
//            } else if (arr1[i] > arr2[j]) {
//                j++;
//            } else {
//                resultSet.add(arr1[i]);
//                i++;
//                j++;
//            }
//        }
//        return resultSet.stream().mapToInt(Integer::intValue).toArray();
//    }
//
//    @Algorithm(complexity = @Complexity(runtime = LINEAR, space = LINEAR), target = OUT_OF_PLACE)
//    static int[] reverse(int[] arr) {
//        if (arr == null)
//            throw new IllegalArgumentException("Array is null");
//        var reversedArr = new int[arr.length];
//        for (int i = arr.length - 1, j = 0; i >= 0; i--, j++) {
//            reversedArr[j] = arr[i];
//        }
//        return reversedArr;
//    }

}
