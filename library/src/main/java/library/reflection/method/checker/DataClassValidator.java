package library.reflection.method.checker;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;

class DataClassValidator {

    public static void main(String[] args) {
        checkGetters(Product.class);
        checkGetters(ClothingProduct.class);
        checkSetters(ClothingProduct.class);
    }

    public static void checkSetters(Class<?> type) {
        List<Field> fields = getAllFields(type);
        for (Field field : fields) {
            String setterName = "set" + capitalizeFirstLetter(field.getName());
            Method setterMethod;
            try {
                setterMethod = type.getMethod(setterName, field.getType());
            } catch (NoSuchMethodException e) {
                throw new IllegalStateException(String.format("Setter : %s not found", setterName));
            }
            if (!setterMethod.getReturnType().equals(void.class)) {
                throw new IllegalStateException(String.format("Setter method : %s has to return void", setterName));
            }
        }
    }

    public static void checkGetters(Class<?> type) {
        List<Field> fields = getAllFields(type);
        Map<String, Method> methodNameToMethod = mapMethodNameToMethod(type);

        for (Field field : fields) {
            String getterName = "get" + capitalizeFirstLetter(field.getName());
            if (!methodNameToMethod.containsKey(getterName)) {
                throw new IllegalStateException(String.format("Field : %s doesn't have a getter method", field.getName()));
            }

            Method getter = methodNameToMethod.get(getterName);
            if (!getter.getReturnType().equals(field.getType())) {
                throw new IllegalStateException(
                        String.format("Getter method : %s() has return type %s but expected %s",
                                getter.getName(),
                                getter.getReturnType().getTypeName(),
                                field.getType().getTypeName()));
            }

            if (getter.getParameterCount() > 0) {
                throw new IllegalStateException(String.format("Getter : %s has %d arguments", getterName));
            }
        }
    }

    private static List<Field> getAllFields(Class<?> type) {
        if (type == null || type.equals(Object.class)) {
            return Collections.emptyList();
        }

        Field[] currentClassFields = type.getDeclaredFields();
        List<Field> inheritedFields = getAllFields(type.getSuperclass());
        List<Field> allFields = new ArrayList<>();

        allFields.addAll(Arrays.asList(currentClassFields));
        allFields.addAll(inheritedFields);

        return allFields;
    }

    private static String capitalizeFirstLetter(String fieldName) {
        return fieldName.substring(0, 1).toUpperCase().concat(fieldName.substring(1));
    }

    private static Map<String, Method> mapMethodNameToMethod(Class<?> dataClass) {
        Method[] allMethods = dataClass.getMethods();

        Map<String, Method> nameToMethod = new HashMap<>();
        for (Method method : allMethods) {
            nameToMethod.put(method.getName(), method);
        }

        return nameToMethod;
    }
}
