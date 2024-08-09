package stream.collector;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toCollection;
import static java.util.stream.Collectors.toMap;

class StreamCollectionCollector {

    public static void main(String[] args) {
        lengthByName(List.of("Java", "C++", "Assembly"));
        listCollectors(List.of(1, 2, 3));
        toCollectionSupplier(List.of(1, 2, 3));
    }

    private static void lengthByName(List<String> names) {
        System.out.println(names.stream().collect(toMap(identity(), String::length)));
    }

    @SuppressWarnings("SimplifyStreamApiCallChains")
    private static void listCollectors(List<Integer> numbers) {
        System.out.println(numbers.stream().collect(Collectors.toList()).getClass());
        System.out.println(numbers.stream().collect(Collectors.toUnmodifiableList()).getClass());
        System.out.println(numbers.stream().toList().getClass());
    }

    @SuppressWarnings("SimplifyStreamApiCallChains")
    private static void toCollectionSupplier(List<Integer> numbers) {
        ArrayList<Integer> arrayList = numbers.stream().collect(toCollection(ArrayList::new));
        System.out.println(arrayList);
        HashSet<Integer> hashSet = numbers.stream().collect(toCollection(HashSet::new));
        System.out.println(hashSet);
        ArrayDeque<Integer> arrayDeque = numbers.stream().collect(toCollection(ArrayDeque::new));
        System.out.println(arrayDeque);
    }

}
