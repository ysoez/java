package library.collection.set;

import java.util.HashSet;
import java.util.Set;

class SetDifference {

    public static void main(String[] args) {
        var set = new HashSet<>(Set.of("a", "b", "c"));
        set.removeAll(new HashSet<>(Set.of("c", "d", "e")));
        System.out.println(set);
    }

}
