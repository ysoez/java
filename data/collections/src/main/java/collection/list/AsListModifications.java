package collection.list;

import java.util.Arrays;

class AsListModifications {

    public static void main(String[] args) {
        var list = Arrays.asList(1, 2, 3);
        System.out.println(list.getClass());
        try {
            list.add(4);
        } catch (UnsupportedOperationException e) {
            System.out.println("add() not supported");
        }
        try {
            list.set(0, 9);
            System.out.println("set() is supported");
        } catch (UnsupportedOperationException e) {
            System.out.println("set() not supported");
        }
        System.out.println(list);
    }

}
