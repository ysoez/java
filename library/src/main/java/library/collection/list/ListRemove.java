package library.collection.list;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

class ListRemove {

    public static void main(String[] args) {
        listRemove(List.of(1, 2, 3));
        collectionRemove(List.of(1, 2, 3));
        typeInferenceRemove(List.of(1, 2, 3));
    }

    private static void listRemove(List<Integer> input) {
        List<Integer> numbers = new ArrayList<>(input);
        System.out.println("\nList<Integer> numbers = " + numbers);
        //
        // ~ param type is resolved at compile time
        // ~ method is resolved at runtime
        // ~ the resolved method is List.remove(int)
        //
        numbers.remove(1);
        System.out.println("remove(1) => " + numbers);
    }

    private static void collectionRemove(List<Integer> input) {
        Collection<Integer> numbers = new ArrayList<>(input);
        System.out.println("\nCollection<Integer> numbers = " + numbers);
        //
        // ~ the resolved method is Collection.remove(Object)
        //
        numbers.remove(1);
        System.out.println("remove(1) => " + numbers);
    }

    private static void typeInferenceRemove(List<Integer> input) {
        //
        // ~ the type of numbers reference is ArrayList
        //
        var numbers = new ArrayList<>(input);
        System.out.println("\nvar numbers = " + numbers);
        //
        // ~ the resolved method is Collection.remove(Object)
        //
        numbers.remove(1);
        System.out.println("remove(1) => " + numbers);
    }

}
