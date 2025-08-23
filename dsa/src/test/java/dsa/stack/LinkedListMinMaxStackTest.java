package dsa.stack;

class LinkedListMinMaxStackTest extends MinMaxStackTest {

    @Override
    public MinMaxStack<Integer> newStack() {
        return new LinkedListMinMaxStack<>();
    }

}