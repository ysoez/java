package algorithm;

import java.util.Comparator;
import java.util.PriorityQueue;

class Sort {

    static int[] heapSort(int[] arr) {
        var numbers = new int[]{5, 3, 10, 1, 4, 2};
        var maxHeap = new PriorityQueue<Integer>(Comparator.reverseOrder());
        for (int number : numbers)
            maxHeap.add(number);
        int index = 0;
        while (!maxHeap.isEmpty())
            numbers[index++] = maxHeap.remove();
        return numbers;
    }

}
