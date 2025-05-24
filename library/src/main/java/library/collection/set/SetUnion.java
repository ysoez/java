package library.collection.set;

import java.util.HashSet;
import java.util.Set;

class SetUnion {

    public static void main(String[] args) {
        var set = new HashSet<>(Set.of("a", "b", "c"));
        set.addAll(new HashSet<>(Set.of("c", "d", "e")));
        System.out.println(set);
    }

}
