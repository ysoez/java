package apache.collection;

import org.apache.commons.collections4.IterableUtils;

import java.util.List;

class ApacheFilteredIterable {

    public static void main(String[] args) {
        System.out.println(IterableUtils.filteredIterable(List.of("apple", "banana"), s -> s.startsWith("a")));
    }

}
