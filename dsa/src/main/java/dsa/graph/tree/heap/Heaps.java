package dsa.graph.tree.heap;

import static dsa.array.Arrays.swap;

class Heaps {

    static int leftChildIndex(int index) {
        return index * 2 + 1;
    }

    static int rightChildIndex(int index) {
        return index * 2 + 2;
    }

    // in place
    static void maxHeapify(int[] arr) {
        // 5, 3, 8, 4, 1, 2
        // optimization 1: loop bound = last parent
        // optimization 2: loop direction: go from bottom to up??
        var lastParentIndex = arr.length / 2 - 1;
        for (var i = lastParentIndex; i >= 0; i--)
            maxHeapify(arr, i);
    }

    private static void maxHeapify(int[] arr, int index) {
        var largerIndex = index;
        var leftIndex = leftChildIndex(index);
        if (leftIndex < arr.length && arr[leftIndex] > arr[largerIndex])
            largerIndex = leftIndex;
        var rightIndex = rightChildIndex(index);
        if (rightIndex < arr.length && arr[rightIndex] > arr[largerIndex])
            largerIndex = rightIndex;
        if (index == largerIndex)
            return;
        swap(arr, index, largerIndex);
        //
        // ~ go up
        //
        maxHeapify(arr, largerIndex);
    }

    // using array impl
    static int getKthLargest(int[] arr, int k) {
        if (k < 1 || k > arr.length)
            throw new IllegalArgumentException();
        var heap = new MaxHeap();
        for (int num : arr) {
            heap.insert(num);
        }
        for (int i = 0; i < k - 1; i++) {
            heap.remove();
        }
        return heap.max();
    }

}
