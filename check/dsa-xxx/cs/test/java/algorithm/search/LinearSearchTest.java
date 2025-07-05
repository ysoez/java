package algorithm.search;

import java.util.function.BiFunction;

class LinearSearchTest implements IntArraySearch {

    @Override
    public BiFunction<int[], Integer, Integer> searchAlgorithm() {
        return LinearSearch::search;
    }

}
