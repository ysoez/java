package algorithm.search;

import java.util.function.BiFunction;

class TernarySearchTest implements SortedIntArraySearch {

    @Override
    public BiFunction<int[], Integer, Integer> searchAlgorithm() {
        return TernarySearch::search;
    }

}