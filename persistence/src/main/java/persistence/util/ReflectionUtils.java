package persistence.util;

public final class ReflectionUtils {

    private ReflectionUtils() {
        throw new UnsupportedOperationException("The " + getClass() + " is not instantiable!");
    }

    public static <T> T newInstance(String className) {
        Class clazz = getClass(className);
        return newInstance(clazz);
    }

    @SuppressWarnings("unchecked")
    public static <T> T newInstance(Class clazz) {
        try {
            return (T) clazz.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            throw handleException(e);
        }
    }

    @SuppressWarnings("unchecked")
    public static <T> Class<T> getClass(String className) {
        try {
            return (Class<T>) Class.forName(className, false, Thread.currentThread().getContextClassLoader());
        } catch (ClassNotFoundException e) {
            throw handleException(e);
        }
    }

    private static IllegalArgumentException handleException(Exception e) {
        return new IllegalArgumentException(e);
    }

}
