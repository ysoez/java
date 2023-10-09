package reflection.serialization.config;

import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Path;
import java.util.Scanner;

class ConfigLoader {
    private static final Path GAME_CONFIG_PATH = Path.of("reflection/src/main/java/reflection/serialization/config/game-properties.cfg");
    private static final Path UI_CONFIG_PATH = Path.of("reflection/src/main/java/reflection/serialization/config/user-interface.cfg");

    public static void main(String[] args) throws Exception {
        System.out.println(createConfigObject(GameConfig.class, GAME_CONFIG_PATH));
        System.out.println(createConfigObject(UserInterfaceConfig.class, UI_CONFIG_PATH));
    }

    public static <T> T createConfigObject(Class<T> clazz, Path filePath) throws Exception {
        T configInstance = newInstance(clazz);
        var scanner = new Scanner(filePath);
        while (scanner.hasNextLine()) {
            String configLine = scanner.nextLine();
            String[] nameValuePair = configLine.split("=");
            if (nameValuePair.length != 2) {
                continue;
            }

            String propertyName = nameValuePair[0];
            String propertyValue = nameValuePair[1];
            Field field;
            try {
                field = clazz.getDeclaredField(propertyName);
            } catch (NoSuchFieldException e) {
                System.out.printf("Property %s is unsupported%n", propertyName);
                continue;
            }
            field.setAccessible(true);

            Object parsedValue = field.getType().isArray()
                    ? parseArray(field.getType().getComponentType(), propertyValue)
                    : parseValue(field.getType(), propertyValue);
            field.set(configInstance, parsedValue);
        }
        return configInstance;
    }

    @SuppressWarnings("unchecked")
    private static <T> T newInstance(Class<T> clazz) throws NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
        Constructor<?> constructor = clazz.getDeclaredConstructor();
        constructor.setAccessible(true);
        return (T) constructor.newInstance();
    }

    private static Object parseArray(Class<?> arrayElementType, String value) {
        String[] arrayValues = value.split(",");
        Object arrayObject = Array.newInstance(arrayElementType, arrayValues.length);
        for (int i = 0; i < arrayValues.length; i++) {
            Array.set(arrayObject, i, parseValue(arrayElementType, arrayValues[i]));
        }
        return arrayObject;
    }

    private static Object parseValue(Class<?> type, String value) {
        if (type.equals(int.class)) {
            return Integer.parseInt(value);
        } else if (type.equals(short.class)) {
            return Short.parseShort(value);
        } else if (type.equals(long.class)) {
            return Long.parseLong(value);
        } else if (type.equals(double.class)) {
            return Double.parseDouble(value);
        } else if (type.equals(float.class)) {
            return Float.parseFloat(value);
        } else if (type.equals(String.class)) {
            return value;
        }
        throw new IllegalArgumentException(String.format("%s type is unsupported", type.getTypeName()));
    }
}
