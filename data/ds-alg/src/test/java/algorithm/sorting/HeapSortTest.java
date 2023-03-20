package algorithm.sorting;

import java.util.function.UnaryOperator;

class HeapSortTest implements IntArraySort {

    @Override
    public UnaryOperator<int[]> sortAlgorithm() {
        return HeapSort::sort;
    }

}