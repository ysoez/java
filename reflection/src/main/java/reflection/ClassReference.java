package reflection;

import java.util.HashMap;

@SuppressWarnings("InstantiatingObjectToGetClassObject")
class ClassReference {

    public static void main(String[] args) throws ClassNotFoundException {
        Class<String> stringClass = String.class;
        System.out.println(stringClass);
        Class<?> hashMapClass = new HashMap<String, Integer>().getClass();
        System.out.println(hashMapClass);
        Class<?> mapEntryClass = Class.forName("java.util.Map$Entry");
        System.out.printf(mapEntryClass.toString());
    }

}
