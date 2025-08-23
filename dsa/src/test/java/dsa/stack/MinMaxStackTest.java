package dsa.stack;

import org.junit.jupiter.api.Test;

import java.util.EmptyStackException;

import static org.junit.jupiter.api.Assertions.*;

abstract class MinMaxStackTest extends StackTest {

    @Override
    public abstract MinMaxStack<Integer> newStack();

    @Test
    void minMaxTrackingCorrect() {
        var stack = newStack();

        stack.push(3);
        assertEquals(3, stack.min());
        assertEquals(3, stack.max());

        stack.push(7);
        assertEquals(3, stack.min());
        assertEquals(7, stack.max());

        stack.push(2);
        assertEquals(2, stack.min());
        assertEquals(7, stack.max());

        stack.push(5);
        assertEquals(2, stack.min());
        assertEquals(7, stack.max());

        stack.pop();
        assertEquals(2, stack.min());
        assertEquals(7, stack.max());

        stack.pop();
        assertEquals(3, stack.min());
        assertEquals(7, stack.max());

        stack.pop();
        assertEquals(3, stack.min());
        assertEquals(3, stack.max());

        stack.pop();
        assertTrue(stack.isEmpty());
        assertThrows(EmptyStackException.class, stack::min);
        assertThrows(EmptyStackException.class, stack::max);
    }

}