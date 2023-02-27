package algorithm.reverse;

class IntArrayReverser implements Reverser<int[]> {

    @Override
    public int[] reverse(int[] arr) {
        if (arr == null)
            throw new IllegalArgumentException("Array is null");
        var reversedArr = new int[arr.length];
        for (int i = arr.length - 1, j = 0; i >= 0; i--, j++) {
            reversedArr[j] = arr[i];
        }
        return reversedArr;
    }

}
