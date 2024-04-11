package collection.immutable;

import java.util.List;

class ImmutableList {

    public static void main(String[] args) {
        var list = List.of(1, 2, 3);
        System.out.println(list.getClass());
        try {
            list.add(4);
        } catch (UnsupportedOperationException e) {
            System.out.println("add() not supported");
        }
        try {
            list.set(0, 9);
        } catch (UnsupportedOperationException e) {
            System.out.println("set() not supported");
        }
        System.out.println(list);
    }

}
