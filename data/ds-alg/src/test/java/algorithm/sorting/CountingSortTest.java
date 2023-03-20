package algorithm.sorting;

import java.util.function.UnaryOperator;

class CountingSortTest implements IntArraySort {

    @Override
    public UnaryOperator<int[]> sortAlgorithm() {
        return arr -> CountingSort.sort(arr, 7);
    }

}
