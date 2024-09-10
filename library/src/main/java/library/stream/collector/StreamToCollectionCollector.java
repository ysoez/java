package library.stream.collector;

import java.util.ArrayList;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toCollection;
import static java.util.stream.Collectors.toSet;

class StreamToCollectionCollector {

    public static void main(String[] args) {
        var list = Stream.of("A", "A", "B", "B", "C", "C").toList();
        System.out.println("toList(): class=" + list.getClass().getName() + ", content " + list);

        var set = Stream.of("A", "A", "B", "B", "C", "C").collect(toSet());
        System.out.println("toSet(): class=" + set.getClass().getName() + ", content " + set);

        var arrayList = Stream.of("A", "A", "B", "B", "C", "C").collect(toCollection(ArrayList::new));
        System.out.println("toCollection(): class=" + arrayList.getClass().getName() + ", content " + arrayList);
    }

}
