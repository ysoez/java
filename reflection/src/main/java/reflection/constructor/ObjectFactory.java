package reflection.constructor;

import lombok.ToString;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

class ObjectFactory {

    public static void main(String[] args) throws Exception {
        System.out.println("User() => " + createInstance(User.class));
        System.out.println("User(String name, int age) => " + createInstance(User.class, "John", 20));

        Address address = createInstance(Address.class, "First Street", 10);
        System.out.println("User(Address address, String name, int age) => " + createInstance(User.class, address, "John", 20));
    }

    static <T> T createInstance(Class<T> clazz, Object... args) throws IllegalAccessException, InvocationTargetException, InstantiationException {
        for (Constructor<?> constructor : clazz.getDeclaredConstructors()) {
            if (constructor.getParameterTypes().length == args.length) {
                //noinspection unchecked
                return (T) constructor.newInstance(args);
            }
        }
        throw new InstantiationException("Constructor was not found");
    }

    @ToString
    private static class User {
        private final Address address;
        private final String name;
        private final int age;

        public User() {
            this.name = "anonymous";
            this.age = 0;
            this.address = null;
        }

        public User(String name) {
            this.name = name;
            this.age = 0;
            this.address = null;
        }

        public User(String name, int age) {
            this.name = name;
            this.age = age;
            this.address = null;
        }

        public User(Address address, String name, int age) {
            this.address = address;
            this.name = name;
            this.age = age;
        }
    }

    private record Address(String street, int number) {
    }

}
