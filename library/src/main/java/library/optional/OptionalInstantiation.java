package library.optional;

import java.util.Optional;

class OptionalInstantiation {

    public static void main(String[] args) {
        System.out.println(Optional.empty());
        System.out.println(Optional.ofNullable(null));
        System.out.println(Optional.ofNullable("value"));
        System.out.println(Optional.of("value"));
        System.out.println(Optional.of(null));
    }

}
