package persistence.util;

public final class ReflectionUtils {

    private ReflectionUtils() {
        throw new UnsupportedOperationException();
    }

    public static <T> T newInstance(String className) {
        return newInstance(getClass(className));
    }

    @SuppressWarnings("unchecked")
    public static <T> T newInstance(Class type) {
        try {
            return (T) type.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            throw new IllegalArgumentException(e);
        }
    }

    @SuppressWarnings("unchecked")
    public static <T> Class<T> getClass(String className) {
        try {
            return (Class<T>) Class.forName(className, false, Thread.currentThread().getContextClassLoader());
        } catch (ClassNotFoundException e) {
            throw new IllegalArgumentException(e);
        }
    }

}
