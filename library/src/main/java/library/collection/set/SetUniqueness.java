package library.collection.set;

import java.util.HashSet;
import java.util.List;

class SetUniqueness {

    public static void main(String[] args) {
        System.out.println(new HashSet<>(List.of("a", "a", "b", "b")));
    }

}
