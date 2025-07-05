package data_structure.stack;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MinStackTest implements StackTest {

    @Override
    public Stack<Integer> create(Integer... values) {
        var stack = new MinStack<Integer>();
        for (Integer value : values) {
            stack.push(value);
        }
        return stack;
    }

    @Override
    public Stack<Integer> createEmpty() {
        return new MinStack<>();
    }

    @Test
    void min() {
        var stack = new MinStack<Integer>();
        stack.push(-2);
        stack.push(0);
        stack.push(-3);
        stack.push(1);

        assertEquals(1, stack.peek());
        assertEquals(-3, stack.min());

        assertEquals(1, stack.pop());
        assertEquals(-3, stack.pop());
        assertEquals(-2, stack.min());
    }

}
