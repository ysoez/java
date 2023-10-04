package reflection.inspect;

import java.lang.reflect.Constructor;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class ConstructorInspector {

    public static void main(String [] args) {
        printConstructorsData(Collection.class);
        printConstructorsData(RuntimeException.class);
        printConstructorsData(RoundingMode.class);
    }

    static void printConstructorsData(Class<?> clazz) {
        Constructor<?>[] constructors = clazz.getDeclaredConstructors();
        System.out.printf("Type %s has %d declared constructors%n", clazz.getSimpleName(), constructors.length);
        for (Constructor<?> constructor : constructors) {
            Class<?>[] parameterTypes = constructor.getParameterTypes();
            List<String> parameterTypeNames = Arrays.stream(parameterTypes)
                    .map(Class::getSimpleName)
                    .toList();
            System.out.println(parameterTypeNames);
        }
    }

}
