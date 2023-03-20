package algorithm.sorting;

import java.util.PriorityQueue;

import static java.util.Comparator.naturalOrder;

class HeapSort {

    static int[] sort(int[] arr) {
        var minHeap = new PriorityQueue<Integer>(naturalOrder());
        for (int number : arr)
            minHeap.add(number);
        int index = 0;
        while (!minHeap.isEmpty())
            arr[index++] = minHeap.remove();
        return arr;
    }

}
