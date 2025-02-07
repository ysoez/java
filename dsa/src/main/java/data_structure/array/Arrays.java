package data_structure.array;

import data_structure.Algorithm;
import data_structure.Algorithm.Complexity;

import java.util.HashSet;

import static data_structure.Algorithm.Complexity.Value.CONSTANT;
import static data_structure.Algorithm.Complexity.Value.LINEAR;
import static data_structure.Algorithm.Target.OUT_OF_PLACE;

public class Arrays {

    public static void checkCapacity(int capacity) {
        if (capacity < 0) {
            throw new NegativeArraySizeException();
        }
    }

    public static void checkIndex(int index, int size) {
        if (index < 0 || index >= size) {
            throw new ArrayIndexOutOfBoundsException(index);
        }
    }

    public static void swap(int[] array, int first, int second) {
        var temp = array[first];
        array[first] = array[second];
        array[second] = temp;
    }

    @Algorithm(complexity = @Complexity(runtime = LINEAR, space = CONSTANT))
    public static Integer max(int[] array) {
        if (array == null)
            throw new IllegalArgumentException();
        if (array.length == 0)
            throw new EmptyArrayException();
        int max = array[0];
        for (int i = 1; i < array.length; i++)
            if (array[i] > max)
                max = array[i];
        return max;
    }

    @Algorithm(complexity = @Complexity(runtime = LINEAR, space = LINEAR), target = OUT_OF_PLACE)
    static int[] intersectionForSorted(int[] arr1, int[] arr2) {
        if (arr1 == null || arr2 == null)
            throw new IllegalArgumentException();
        var resultSet = new HashSet<Integer>();
        for (int i = 0, j = 0; i < arr1.length && j < arr2.length; ) {
            if (arr1[i] < arr2[j]) {
                i++;
            } else if (arr1[i] > arr2[j]) {
                j++;
            } else {
                resultSet.add(arr1[i]);
                i++;
                j++;
            }
        }
        return resultSet.stream().mapToInt(Integer::intValue).toArray();
    }

    @Algorithm(complexity = @Complexity(runtime = LINEAR, space = LINEAR), target = OUT_OF_PLACE)
    static int[] reverse(int[] arr) {
        if (arr == null)
            throw new IllegalArgumentException("Array is null");
        var reversedArr = new int[arr.length];
        for (int i = arr.length - 1, j = 0; i >= 0; i--, j++) {
            reversedArr[j] = arr[i];
        }
        return reversedArr;
    }

}
