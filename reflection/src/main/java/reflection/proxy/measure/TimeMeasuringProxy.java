package reflection.proxy.measure;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

class TimeMeasuringProxy {

    @SuppressWarnings("unchecked")
    public static <T> T newInstance(Object originalObject) {
        Class<?>[] interfaces = originalObject.getClass().getInterfaces();
        var proxyHandler = new TimeMeasuringProxyHandler(originalObject);
        return (T) Proxy.newProxyInstance(originalObject.getClass().getClassLoader(), interfaces, proxyHandler);
    }

    private record TimeMeasuringProxyHandler(Object originalObject) implements InvocationHandler {
        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            Object result;
            System.out.printf("Measuring Proxy: Before Executing method : %s()%n", method.getName());
            long startTime = System.nanoTime();
            try {
                result = method.invoke(originalObject, args);
            } catch (InvocationTargetException e) {
                // ~ preserve contract
                throw e.getTargetException();
            }
            long endTime = System.nanoTime();
            System.out.printf("Measuring Proxy: Execution of %s() took %dns %n", method.getName(), endTime - startTime);

            return result;
        }
    }
}
