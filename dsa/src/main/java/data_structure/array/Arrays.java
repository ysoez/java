package data_structure.array;

import data_structure.Algorithm;
import data_structure.Algorithm.Complexity;

import static data_structure.Algorithm.Complexity.Value.CONSTANT;
import static data_structure.Algorithm.Complexity.Value.LINEAR;

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

}
