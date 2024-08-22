package library.reflection.constructor;

import java.lang.reflect.Constructor;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

class ConstructorInspector {

    public static void main(String[] args) {
        printConstructorsData(Collection.class);
        printConstructorsData(RuntimeException.class);
        printConstructorsData(RoundingMode.class);
    }

    static void printConstructorsData(Class<?> type) {
        Constructor<?>[] constructors = type.getDeclaredConstructors();
        System.out.printf("Type %s has %d declared constructors%n", type.getSimpleName(), constructors.length);
        for (Constructor<?> constructor : constructors) {
            Class<?>[] parameterTypes = constructor.getParameterTypes();
            List<String> parameterTypeNames = Arrays.stream(parameterTypes)
                    .map(Class::getSimpleName)
                    .toList();
            System.out.println(type.getSimpleName() + ": " + parameterTypeNames);
        }
    }

}
