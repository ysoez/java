package algorithm.map;

import util.Algorithm;
import util.Algorithm.Complexity;

import java.util.PriorityQueue;

import static java.util.Comparator.reverseOrder;
import static util.Algorithm.Complexity.Value.*;
import static util.Algorithm.Target.IN_PLACE;

class Heaps {

    @Algorithm(
        complexity = @Complexity(runtime = LINEAR, space = CONSTANT),
        target = IN_PLACE
    )
    static void heapify(int[] array) {
        Heapify.allNodes(array);
    }

    @Algorithm(
        complexity = @Complexity(runtime = LOGARITHMIC, space = CONSTANT),
        target = IN_PLACE
    )
    static void heapifyEfficienly(int[] array) {
        Heapify.parentNodesOnly(array);
    }

    static int getKthLargest(int[] array, int kLargest) {
        if (kLargest < 1 || kLargest > array.length)
            throw new IllegalArgumentException();
        var maxHeap = new PriorityQueue<Integer>(reverseOrder());
        for (int number : array)
            maxHeap.add(number);
        for (int i = 0; i < kLargest - 1; i++)
            maxHeap.remove();
        return maxHeap.peek();
    }

    private static void swap(int[] array, int first, int second) {
        var temp = array[first];
        array[first] = array[second];
        array[second] = temp;
    }

    /**
     * In a perfect binary tree half of the nodes are in the last level.
     * Applying this algorithm for every node is not optimal (we do not heapify leaf nodes).
     */
    private static class Heapify {

        public static void parentNodesOnly(int[] array) {
            int lastParentIndex = array.length / 2 - 1;
            for (var i = lastParentIndex; i >= 0; i--)
                heapify(array, i);
        }

        public static void allNodes(int[] array) {
            for (var i = 0; i < array.length; i++)
                heapify(array, i);
        }

        private static void heapify(int[] array, int index) {
            var largerIndex = index;
            var leftChildIndex = index * 2 + 1;
            if (leftChildIndex < array.length && array[leftChildIndex] > array[largerIndex])
                largerIndex = leftChildIndex;
            var rightChildIndex = index * 2 + 2;
            if (rightChildIndex < array.length && array[rightChildIndex] > array[largerIndex])
                largerIndex = rightChildIndex;
            if (index == largerIndex)
                return;
            swap(array, index, largerIndex);
            heapify(array, largerIndex);
        }
    }

}
