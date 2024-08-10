package data_structure.stack;

class ArrayStackTest implements StackTest {

    @Override
    public Stack<Integer> create(Integer... values) {
        return new ArrayStack<>(values);
    }

    @Override
    public Stack<Integer> createEmpty() {
        return new ArrayStack<>();
    }

}