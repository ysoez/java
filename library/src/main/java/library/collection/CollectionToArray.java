package library.collection;

import java.util.Arrays;
import java.util.List;

class CollectionToArray {

    public static void main(String[] args) {
        List<String> words = List.of("hello", "world");
        System.out.println(Arrays.toString(words.toArray()));
    }

}
