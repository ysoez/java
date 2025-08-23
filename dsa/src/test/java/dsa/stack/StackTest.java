package dsa.stack;

import org.junit.jupiter.api.Test;

import java.util.EmptyStackException;

import static org.junit.jupiter.api.Assertions.*;

abstract class StackTest {

    private final Stack<Integer> stack = newStack();

    public abstract Stack<Integer> newStack();

    @Test
    void testStackIsEmpty() {
        assertTrue(stack.isEmpty());
    }

    @Test
    void testPeekOnEmptyStackThrowsException() {
        assertThrows(EmptyStackException.class, stack::peek);
    }

    @Test
    void testPopOnEmptyStackThrowsException() {
        assertThrows(EmptyStackException.class, stack::pop);
    }

    @Test
    void testPushMakesStackNonEmpty() {
        stack.push(42);
        assertFalse(stack.isEmpty());
    }

    @Test
    void testPushThenPopReturnsSameElement() {
        stack.push(1);
        assertEquals(1, stack.pop());
    }

    @Test
    void testPushThenPeekReturnsSameElementWithoutRemoving() {
        stack.push(1);
        assertEquals(1, stack.peek());
        assertFalse(stack.isEmpty());
        assertEquals(1, stack.pop());
    }

    @Test
    void testMultiplePushPopRespectsLifoOrder() {
        stack.push(1);
        stack.push(2);
        stack.push(3);

        assertEquals(3, stack.pop());
        assertEquals(2, stack.pop());
        assertEquals(1, stack.pop());
        assertTrue(stack.isEmpty());
    }

}