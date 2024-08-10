package algorithm.sorting;

import java.util.function.UnaryOperator;

class BucketSortTest implements IntArraySort{

    @Override
    public UnaryOperator<int[]> sortAlgorithm() {
        return arr -> BucketSort.sort(arr, 3);
    }

}