package library.reflection.method.polymophism;

import library.reflection.method.polymophism.client.FileLogger;
import library.reflection.method.polymophism.client.UdpClient;
import library.reflection.method.polymophism.client.DatabaseClient;
import library.reflection.method.polymophism.client.HttpClient;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class RequestInvoker {

    public static void main(String[] args) throws Throwable {
        var databaseClient = new DatabaseClient();
        var httpClient1 = new HttpClient("123.456.789.0");
        var httpClient2 = new HttpClient("11.33.55.0");
        var fileLogger = new FileLogger();
        var udpClient = new UdpClient();

        List<Class<?>> methodParameterTypes = Arrays.asList(new Class<?>[]{String.class});
        Map<Object, Method> requestExecutors = groupExecutors(Arrays.asList(databaseClient, httpClient1, httpClient2, fileLogger, udpClient), methodParameterTypes);

        String requestBody = "request data";
        executeAll(requestExecutors, requestBody);
    }

    private static void executeAll(Map<Object, Method> requestExecutors, Object... arguments) throws Throwable {
        try {
            for (Map.Entry<Object, Method> requestExecutorEntry : requestExecutors.entrySet()) {
                Object requestExecutor = requestExecutorEntry.getKey();
                Method method = requestExecutorEntry.getValue();
                Boolean result = (Boolean) method.invoke(requestExecutor, arguments);
                if (result != null && result.equals(Boolean.FALSE)) {
                    System.out.println("Received negative result. Aborting ...");
                    return;
                }
            }
        } catch (InvocationTargetException e) {
            throw e.getTargetException();
        }
    }

    private static Map<Object, Method> groupExecutors(List<Object> requestExecutors, List<Class<?>> methodParameterTypes) {
        Map<Object, Method> instanceToMethod = new HashMap<>();
        for (Object requestExecutor : requestExecutors) {
            Method[] allMethods = requestExecutor.getClass().getDeclaredMethods();
            for (Method method : allMethods) {
                if (Arrays.asList(method.getParameterTypes()).equals(methodParameterTypes)) {
                    instanceToMethod.put(requestExecutor, method);
                }
            }
        }
        return instanceToMethod;
    }

}
