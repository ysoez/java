package data_structure.stack;

import org.junit.jupiter.api.Test;

import java.util.EmptyStackException;

import static org.junit.jupiter.api.Assertions.*;

interface StackTest {

    Stack<Integer> create(Integer... values);

    Stack<Integer> createEmpty();

    @Test
    default void isEmpty() {
        assertTrue(createEmpty().isEmpty());
        assertFalse(create(10).isEmpty());
    }

    @Test
    default void push() {
        var stack = createEmpty();
        stack.push(10);
        assertEquals(stack.peek(), 10);

        stack = createEmpty();
        stack.push(10);
        assertEquals(stack.peek(), 10);

        stack.push(20);
        assertEquals(stack.peek(), 20);

        stack.push(30);
        assertEquals(stack.peek(), 30);
    }

    @Test
    default void peek() {
        var stack = createEmpty();
        assertThrows(EmptyStackException.class, stack::peek);

        stack = create(10);
        assertEquals(stack.peek(), 10);

        stack = create(10, 20);
        assertEquals(stack.peek(), 20);

        stack = create(10, 20, 30);
        assertEquals(stack.peek(), 30);
    }

    @Test
    default void pop() {
        var stack = createEmpty();
        assertThrows(EmptyStackException.class, stack::pop);

        stack = create(10);
        assertEquals(stack.pop(), 10);
        assertTrue(stack.isEmpty());

        stack = create(10, 20);
        assertEquals(stack.pop(), 20);
        assertEquals(stack.peek(), 10);

        stack = create(10, 20, 30);
        assertEquals(stack.pop(), 30);
        assertEquals(stack.peek(), 20);
    }

}
