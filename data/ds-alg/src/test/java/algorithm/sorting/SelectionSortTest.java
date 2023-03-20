package algorithm.sorting;

import java.util.function.UnaryOperator;

class SelectionSortTest implements IntArraySort {

    @Override
    public UnaryOperator<int[]> sortAlgorithm() {
        return SelectionSort::sort;
    }

}