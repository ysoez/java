package algorithm.search;

class ExponentialSearch {

    static int search(int[] arr, int value) {
        int bound = 1;
        while (bound < arr.length && arr[bound] < value)
            bound *= 2;
        int left = bound / 2;
        int right = Math.min(bound, arr.length - 1);
        return BinarySearch.searchRecursive(arr, value, left, right);
    }

}
