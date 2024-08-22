package library.reflection.annotation;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;

class Annotations {

    static Set<Method> getAllAnnotatedMethod(Class<?> type, Class<? extends Annotation> annotationType) {
        Set<Method> annotatedMethods = new HashSet<>();
        for (Method method : type.getDeclaredMethods()) {
            if (method.isAnnotationPresent(annotationType)) {
                annotatedMethods.add(method);
            }
        }
        return annotatedMethods;
    }

}
