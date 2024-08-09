package library.collection;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

class ArrayListResize {

    public static void main(String[] args) {
        int elementsCount = 100_000_000;
        var start = new Date();
        List<Element> list = new ArrayList<>(elementsCount);
        for (int i = 0; i < elementsCount; i++) {
            list.add(new Element(i, String.valueOf(i)));
        }
        var end = new Date();
        System.out.printf("Execution time: %s ms", end.getTime() - start.getTime());
    }

    private record Element(int index, String value) {}

}
