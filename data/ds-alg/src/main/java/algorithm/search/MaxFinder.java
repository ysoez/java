package algorithm.search;

import util.Complexity;

class MaxFinder implements Finder<int[], Integer> {

    @Override
    @Complexity(runtime = "O(n)", space = "O(1)")
    public Integer search(int[] array) {
        if (array == null || array.length == 0)
            throw new IllegalArgumentException("Array must contain at least 1 element");
        int max = array[0];
        for (int i = 1; i < array.length; i++)
            if (array[i] > max)
                max = array[i];
        return max;
    }

}
