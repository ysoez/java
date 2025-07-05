package algorithm.search;

import org.junit.jupiter.api.Nested;

import java.util.function.BiFunction;

class BinarySearchTest {

    @Nested
    class Recursive implements SortedIntArraySearch {
        @Override
        public BiFunction<int[], Integer, Integer> searchAlgorithm() {
            return BinarySearch::searchRecursive;
        }
    }

    @Nested
    class Iterative implements SortedIntArraySearch {
        @Override
        public BiFunction<int[], Integer, Integer> searchAlgorithm() {
            return BinarySearch::searchIterative;
        }
    }

}
