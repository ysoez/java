package library.concurrency.collection;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

class CopyOnWriteArrayListIteratorRemove {

    public static void main(String[] ags) throws InterruptedException {
        var arrayList = new ArrayList<String>() {{
            add("1");
            add("2");
            add("3");
            add("4");
        }};
        iteratorRemoveLast(arrayList);
        var copyOnWriteArrayList = new CopyOnWriteArrayList<>(List.of("1,", "2", "3", "4"));
        iteratorRemoveLast(copyOnWriteArrayList);
    }

    private static void iteratorRemoveLast(Iterable<String> iterable) {
        var iterator = iterable.iterator();
        while (iterator.hasNext()) {
            if (iterator.next().equals("4")) {
                iterator.remove();
            }
        }
        System.out.println(iterable);
    }

}
