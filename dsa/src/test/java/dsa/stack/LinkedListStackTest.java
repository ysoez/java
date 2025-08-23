package dsa.stack;

class LinkedListStackTest extends StackTest {

    @Override
    public Stack<Integer> newStack() {
        return new LinkedListStack<>();
    }

}