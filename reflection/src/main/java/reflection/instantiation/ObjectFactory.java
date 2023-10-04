package reflection.instantiation;

import lombok.ToString;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class ObjectFactory {

    public static void main(String[] args) throws Exception {
        Address address = createInstance(Address.class, "First Street", 10);
        User user = createInstance(User.class, address, "John", 20);
        System.out.println(user);
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

    @ToString()
    static class User {
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

    @ToString
    public static class Address {
        private final String street;
        private final int number;

        public Address(String street, int number) {
            this.street = street;
            this.number = number;
        }
    }

}
