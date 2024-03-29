package data_structure.array;

public class Arrays {

    public static void checkCapacity(int capacity) {
        if (capacity < 0) {
            throw new IllegalArgumentException("Array must have a positive capacity");
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

}
