package dsa.stack;

class QueueStackTest extends StackTest {

    @Override
    public Stack<Integer> newStack() {
        return new QueueStack<>();
    }

}