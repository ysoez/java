package library.collection.immutable;

import java.util.Set;

class ImmutableSet {

    public static void main(String[] args) {
        try {
            var set = Set.of(1, 1, 1);
            System.out.println(set);
        } catch (IllegalArgumentException e) {
            System.err.println("no duplicates");
        }
    }

}
