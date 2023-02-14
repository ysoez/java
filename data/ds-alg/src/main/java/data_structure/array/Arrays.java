package data_structure.array;

class Arrays {

    static void checkCapacity(int capacity) {
        if (capacity < 0) {
            throw new IllegalArgumentException("Array must have a positive capacity");
        }
    }

    static void checkIndex(int index, int size) {
        if (index < 0 || index >= size) {
            throw new ArrayIndexOutOfBoundsException(index);
        }
    }

}
