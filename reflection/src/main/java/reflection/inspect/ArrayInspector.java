package reflection.inspect;

import java.lang.reflect.Array;
import java.util.Arrays;

class ArrayInspector {

    public static void main(String[] args) {
        int[] oneDimensionalArray = {1, 2};
        System.out.println(Arrays.toString(oneDimensionalArray));
        printArrayMetadata(oneDimensionalArray);

        double[][] twoDimensionalArray = {{1.5, 2.5}, {3.5, 4.5}};
        System.out.println(Arrays.deepToString(twoDimensionalArray));
        printArrayMetadata(twoDimensionalArray);

        printArrayValues(twoDimensionalArray);
    }

    static void printArrayValues(Object arrayObject) {
        int arrayLength = Array.getLength(arrayObject);
        System.out.print("[");
        for (int i = 0; i < arrayLength; i++) {
            Object element = Array.get(arrayObject, i);

            if (element.getClass().isArray()) {
                printArrayValues(element);
            } else {
                System.out.print(element);
            }

            if (i != arrayLength - 1) {
                System.out.print(" , ");
            }
        }
        System.out.print("]");
    }

    static void printArrayMetadata(Object arrayObject) {
        Class<?> type = arrayObject.getClass();
        System.out.printf("Is array : %s%n", type.isArray());
        Class<?> arrayComponentType = type.getComponentType();
        System.out.printf("This is an array of type : %s%n\n", arrayComponentType.getTypeName());
    }

}
