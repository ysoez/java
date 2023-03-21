package algorithm.search;

import java.util.function.BiFunction;

class ExponentialSearchTest implements SortedIntArraySearch {

    @Override
    public BiFunction<int[], Integer, Integer> searchAlgorithm() {
        return ExponentialSearch::search;
    }

}