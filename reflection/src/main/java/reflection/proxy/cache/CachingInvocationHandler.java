package reflection.proxy.cache;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class CachingInvocationHandler implements InvocationHandler {

    // ~ map a method name to a method cache
    // ~ each cache is a map from a list of arguments to a method result
    private final Map<String, Map<List<Object>, Object>> cache = new HashMap<>();
    private final Object realObject;

    CachingInvocationHandler(Object realObject) {
        this.realObject = realObject;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        try {
            if (!isMethodCacheable(method)) {
                return method.invoke(realObject, args);
            }
            if (isInCache(method, args)) {
                return getFromCache(method, args);
            }
            Object result = method.invoke(realObject, args);
            putInCache(method, args, result);
            return result;
        } catch (InvocationTargetException e) {
            throw e.getTargetException();
        }
    }

    private boolean isMethodCacheable(Method method) {
        return method.isAnnotationPresent(Cacheable.class);
    }

    private boolean isInCache(Method method, Object[] args) {
        if (!cache.containsKey(method.getName())) {
            return false;
        }
        List<Object> argumentsList = Arrays.asList(args);
        Map<List<Object>, Object> argumentsToReturnValue = cache.get(method.getName());
        return argumentsToReturnValue.containsKey(argumentsList);
    }

    private void putInCache(Method method, Object[] args, Object result) {
        if (!cache.containsKey(method.getName())) {
            cache.put(method.getName(), new HashMap<>());
        }
        List<Object> argumentsList = Arrays.asList(args);
        Map<List<Object>, Object> argumentsToReturnValue = cache.get(method.getName());
        argumentsToReturnValue.put(argumentsList, result);
    }

    private Object getFromCache(Method method, Object[] args) {
        if (!cache.containsKey(method.getName())) {
            throw new IllegalStateException(String.format("Result of method: %s is not in the cache", method.getName()));
        }
        List<Object> argumentsList = Arrays.asList(args);
        Map<List<Object>, Object> argumentsToReturnValue = cache.get(method.getName());
        if (!argumentsToReturnValue.containsKey(argumentsList)) {
            throw new IllegalStateException(String.format("Result of method: %s and arguments: %s is not in the cache",
                    method.getName(),
                    argumentsList));
        }
        return argumentsToReturnValue.get(argumentsList);
    }

}
