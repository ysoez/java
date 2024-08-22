package library.reflection;

import java.util.HashSet;
import java.util.Set;

class ClassUtils {

    static Set<Class<?>> getAllImplementedInterfaces(Class<?> type) {
        Set<Class<?>> allImplementedInterfaces = new HashSet<>();
        for (Class<?> currentInterface : type.getInterfaces()) {
            allImplementedInterfaces.add(currentInterface);
            allImplementedInterfaces.addAll(getAllImplementedInterfaces(currentInterface));
        }
        return allImplementedInterfaces;
    }

}
