package library.collection.immutable;

import java.util.List;

class ImmutableCollection {

    public static void main(String[] args) {
        nullValuesNotAllowed();
    }

    private static void nullValuesNotAllowed() {
        try {
            var list = List.of(1, 2, 3, null);
            System.out.println(list);
        } catch (NullPointerException e) {
            System.err.println("null not allowed");
        }
    }

}
