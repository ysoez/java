package algorithm.search;

class JumpSearch {

    static int search(int[] arr, int value) {
        int blockSize = (int) Math.sqrt(arr.length);
        int start = 0;
        int next = blockSize;
        while (start < arr.length && arr[next - 1] < value) {
            start = next;
            next += blockSize;
            if (next > arr.length)
                next = arr.length;
        }
        for (var i = start; i < next; i++)
            if (arr[i] == value)
                return i;
        return -1;
    }

}
