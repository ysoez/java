package dsa.sort;

import dsa.tree.heap.MaxHeap;

class HeapSort implements Sort {

    @Override
    public int[] asc(int[] ints) {
        var heap = new MaxHeap();
        for (int num : ints)
            heap.insert(num);
        for (int i = ints.length - 1; i >= 0; i--)
            ints[i] = heap.remove();
        return ints;
    }

    @Override
    public int[] desc(int[] ints) {
        var heap = new MaxHeap();
        for (int num : ints)
            heap.insert(num);
        for (int i = 0; i < ints.length; i++)
            ints[i] = heap.remove();
        return ints;
    }

}
