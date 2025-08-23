package dsa.challenge;

import static dsa.array.Arrays.checkCapacity;

class TwoStack {

    private final int[] items;
    private int top1;
    private int top2;

    TwoStack(int capacity) {
        checkCapacity(capacity);
        items = new int[capacity];
        top1 = -1;
        top2 = capacity;
    }

    void push1(int item) {
        if (isFull1())
            throw new IllegalStateException();
        items[++top1] = item;
    }

    int pop1() {
        if (isEmpty1())
            throw new IllegalStateException();
        return items[top1--];
    }

    boolean isEmpty1() {
        return top1 == -1;
    }

    boolean isFull1() {
        return top1 + 1 == top2;
    }

    void push2(int item) {
        if (isFull2())
            throw new IllegalStateException();
        items[--top2] = item;
    }

    int pop2() {
        if (isEmpty2())
            throw new IllegalStateException();
        return items[top2++];
    }

    boolean isEmpty2() {
        return top2 == items.length;
    }

    boolean isFull2() {
        return top2 - 1 == top1;
    }

}
