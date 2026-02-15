package apache.collection;

import org.apache.commons.collections4.CollectionUtils;

import java.util.Arrays;
import java.util.List;

class ApacheCollectionUtils {

    public static void main(String[] args) {
        List<String> listA = Arrays.asList("a", "b", "c");
        List<String> listB = Arrays.asList("b", "c", "d");
        System.out.println("union: " + CollectionUtils.union(listA, listB));
        System.out.println("intersection: " + CollectionUtils.intersection(listA, listB));
        System.out.println("subtract: " + CollectionUtils.subtract(listA, listB));
    }

}
