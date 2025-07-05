package algorithm.sorting;

import java.util.function.UnaryOperator;

class QuickSortTest implements IntArraySort{

    @Override
    public UnaryOperator<int[]> sortAlgorithm() {
        return arr -> QuickSort.sort(arr, 0, arr.length - 1);
    }

}