package dsa.stack;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ArrayStackTest extends StackTest {

    @Override
    public Stack<Integer> newStack() {
        return new ArrayStack<>(10);
    }

    @Test
    void testFixedSizeStack() {
        var stack = new ArrayStack<>(3);
        stack.push(1);
        stack.push(2);
        stack.push(3);

        assertThrows(FullStackException.class, () -> stack.push(4));
    }

}
