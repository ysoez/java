package library.collection.list;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

class ListShuffle {

    public static void main(String[] args) {
        shuffleImmutable();
        shuffleList();
    }

    @SuppressWarnings({"CallToPrintStackTrace", "DataFlowIssue"})
    private static void shuffleImmutable() {
        try {
            Collections.shuffle(List.of(1, 2, 3));
        } catch (UnsupportedOperationException e) {
            e.printStackTrace();
        }
    }

    private static void shuffleList() {
        var numbers = new ArrayList<>(List.of(1, 2, 3));
        Collections.shuffle(numbers);
        System.out.println(numbers);
    }

}
