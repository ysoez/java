package algorithm.search;

import java.util.function.BiFunction;

class JumpSearchTest implements SortedIntArraySearch {

    @Override
    public BiFunction<int[], Integer, Integer> searchAlgorithm() {
        return JumpSearch::search;
    }

}