package algorithm.sorting;

import java.util.function.UnaryOperator;

class InsertionSortTest implements IntArraySort {

    @Override
    public UnaryOperator<int[]> sortAlgorithm() {
        return InsertionSort::sort;
    }

}