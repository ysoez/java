package collection.immutable;

import java.util.List;

class ImmutableCollectionNullability {

    public static void main(String[] args) {
        try {
            var list = List.of(1, 2, 3, null);
            System.out.println(list);
        } catch (NullPointerException e) {
            System.err.println("null not allowed");
        }
    }

}
