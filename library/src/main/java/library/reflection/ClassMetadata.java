package library.reflection;

import java.math.RoundingMode;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

@SuppressWarnings("InstantiatingObjectToGetClassObject")
class ClassMetadata {

    public static void main(String[] args) throws ClassNotFoundException {
        printClassInfo(String.class, new HashMap<>().getClass(), Class.forName("java.util.Map$Entry"));
        printClassInfo(Collection.class, boolean.class, int[][].class, RoundingMode.class, new Iterator<>() {
            @Override
            public boolean hasNext() {
                return false;
            }

            @Override
            public Object next() {
                return null;
            }
        }.getClass());

    }

    private static void printClassInfo(Class<?>... classes) {
        for (Class<?> clazz : classes) {
            System.out.println(String.format("class name : %s, class package name : %s",
                    clazz.getSimpleName(),
                    clazz.getPackageName()));
            Class<?>[] implementedInterfaces = clazz.getInterfaces();
            for (Class<?> implementedInterface : implementedInterfaces) {
                System.out.println(String.format("class %s implements : %s",
                        clazz.getSimpleName(),
                        implementedInterface.getSimpleName()));
            }
            System.out.println("Is array : " + clazz.isArray());
            System.out.println("Is primitive : " + clazz.isPrimitive());
            System.out.println("Is enum : " + clazz.isEnum());
            System.out.println("Is interface : " + clazz.isInterface());
            System.out.println("Is anonymous :" + clazz.isAnonymousClass());

            System.out.println();
        }
    }

}
