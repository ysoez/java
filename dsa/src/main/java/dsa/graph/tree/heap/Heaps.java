package dsa.graph.tree.heap;

import dsa.Algorithm;
import dsa.Algorithm.Complexity;

import static dsa.Algorithm.Complexity.*;
import static dsa.array.Arrays.swap;

class Heaps {

    @Algorithm(complexity = @Complexity(runtime = CONSTANT, space = CONSTANT))
    static int leftChildIndex(int index) {
        return index * 2 + 1;
    }

    @Algorithm(complexity = @Complexity(runtime = CONSTANT, space = CONSTANT))
    static int rightChildIndex(int index) {
        return index * 2 + 2;
    }

    @Algorithm(complexity = @Complexity(runtime = CONSTANT, space = CONSTANT))
    static int parentIndex(int index) {
        return (index - 1) / 2;
    }

    @Algorithm(complexity = @Complexity(runtime = LOGARITHMIC, space = LOGARITHMIC))
    static void maxHeapOf(int[] arr) {
        //
        // ~ iterate over parent nodes only
        // ~ starting from the deepest parent node reduces the number of recursive calls
        //
        var lastParentIndex = arr.length / 2 - 1;
        for (var i = lastParentIndex; i >= 0; i--)
            maxHeapOf(arr, i);
    }

    private static void maxHeapOf(int[] arr, int index) {
        //
        // ~ assume parent is the largest
        //
        var largerIndex = index;
        //
        // ~ resolve the index of the largest value
        //
        var leftIndex = leftChildIndex(index);
        if (leftIndex < arr.length && arr[leftIndex] > arr[largerIndex])
            largerIndex = leftIndex;
        var rightIndex = rightChildIndex(index);
        if (rightIndex < arr.length && arr[rightIndex] > arr[largerIndex])
            largerIndex = rightIndex;
        //
        // ~ parent is the largest (no op)
        //
        if (index == largerIndex)
            return;
        //
        // ~ swap with a larger child
        //
        swap(arr, index, largerIndex);
        //
        // ~ go down the subtree
        //
        maxHeapOf(arr, largerIndex);
    }


    @Algorithm(complexity = @Complexity(runtime = LOGARITHMIC, space = LINEAR))
    static int getKthLargest(int[] arr, int k) {
        if (k < 1 || k > arr.length)
            throw new IllegalArgumentException();
        var heap = new MaxHeap<Integer>();
        for (int num : arr)
            heap.insert(num);
        for (int i = 0; i < k - 1; i++)
            heap.remove();
        return heap.max();
    }

}
