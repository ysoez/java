package dsa.stack;

class ArrayMinMaxStackTest extends MinMaxStackTest {

    @Override
    public MinMaxStack<Integer> newStack() {
        return new ArrayMinMaxStack<>(10);
    }

}