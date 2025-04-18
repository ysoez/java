package library.reflection.field;

import java.lang.reflect.Field;

class ObjectSizeCalculator {

    private static final long HEADER_SIZE = 12;
    private static final long REFERENCE_SIZE = 4;

    public static void main(String... args) throws IllegalAccessException {
        AccountSummary object = new AccountSummary("John", "Smith", (short) 20, 100_000);
        System.out.println(sizeOf(object));
    }

    // ~ sizeOf(object) = {header size} + {object reference} + {sum of the sizes of all its fields}
    private static long sizeOf(Object object) throws IllegalAccessException {
        long size = HEADER_SIZE + REFERENCE_SIZE;
        for (Field field : object.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            if (field.getType().isPrimitive()) {
                size += sizeOfPrimitiveType(field.getType());
            } else {
                size += sizeOfString((String) field.get(object));
            }
        }
        return size;
    }

    private static long sizeOfPrimitiveType(Class<?> primitiveType) {
        if (primitiveType.equals(int.class)) {
            return 4;
        } else if (primitiveType.equals(long.class)) {
            return 8;
        } else if (primitiveType.equals(float.class)) {
            return 4;
        } else if (primitiveType.equals(double.class)) {
            return 8;
        } else if (primitiveType.equals(byte.class)) {
            return 1;
        } else if (primitiveType.equals(short.class)) {
            return 2;
        }
        throw new IllegalArgumentException(String.format("Type: %s is not supported", primitiveType));
    }

    private static long sizeOfString(String inputString) {
        int stringBytesSize = inputString.getBytes().length;
        return HEADER_SIZE + REFERENCE_SIZE + stringBytesSize;
    }

    private record AccountSummary(String firstName, String lastName, short address, int salary) {
    }

}
