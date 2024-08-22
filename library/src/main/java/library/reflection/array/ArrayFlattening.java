package library.reflection.array;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

class ArrayFlattening {

    public static void main(String[] args) {
        System.out.println(concat(Integer.class, 1, 2, 3, 4, 5));
        System.out.println(concat(int.class, 1, 2, 3, new int[]{4, 5, 6}, 7));
        System.out.println(concat(String.class, new String[]{"a", "b"}, "c", new String[]{"d", "e"}));
    }

    private static <T> T concat(Class<T> type, Object... arguments) {
        if (arguments.length == 0) {
            return null;
        }
        List<Object> elements = new ArrayList<>();
        for (Object argument : arguments) {
            if (argument.getClass().isArray()) {
                int length = Array.getLength(argument);

                for (int i = 0; i < length; i++) {
                    elements.add(Array.get(argument, i));
                }
            } else {
                elements.add(argument);
            }
        }

        Object flattenedArray = Array.newInstance(type, elements.size());
        for (int i = 0; i < elements.size(); i++) {
            Array.set(flattenedArray, i, elements.get(i));
        }

        return (T) flattenedArray;
    }

}
