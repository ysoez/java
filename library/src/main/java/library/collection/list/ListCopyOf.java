package library.collection.list;

import java.util.Arrays;
import java.util.List;

class ListCopyOf {

    @SuppressWarnings("ConstantValue")
    public static void main(String[] args) {
        var asList = Arrays.asList(1, 2, 3);
        var listOf = List.of(1, 2, 3);
        System.out.println(asList == List.copyOf(asList));
        System.out.println(listOf == List.copyOf(listOf));
    }

}
