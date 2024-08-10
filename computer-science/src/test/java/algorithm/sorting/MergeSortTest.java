package algorithm.sorting;

import java.util.function.UnaryOperator;

class MergeSortTest implements IntArraySort {

    @Override
    public UnaryOperator<int[]> sortAlgorithm() {
        return MergeSort::sort;
    }

}