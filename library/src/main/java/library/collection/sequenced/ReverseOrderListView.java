package library.collection.sequenced;

import java.util.ArrayList;
import java.util.List;

class ReverseOrderListView {

    public static void main(String[] args) {
        List<String> list = new ArrayList<>(List.of("a", "b", "c"));
        List<String> reversedList = list.reversed();

        System.out.println("original: " + list.hashCode() + " - " + list);
        System.out.println("reversed: " + reversedList.hashCode() + " - " + reversedList);
        System.out.println("list == reversedList.reversed(): " + (list == reversedList.reversed()));

        // ~ update original list
        System.out.println();
        System.out.println("list.set(2, \"X\")");
        list.set(2, "X");
        System.out.println("original: " + list);
        System.out.println("reversed: " + reversedList);

        // ~ update reversed view
        System.out.println();
        System.out.println("reversedList.set(1, \"Y\")");
        reversedList.set(1, "Y");
        System.out.println("original: " + list);
        System.out.println("reversed: " + reversedList);
    }

}
