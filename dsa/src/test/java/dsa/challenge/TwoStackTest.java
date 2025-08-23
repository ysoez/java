package dsa.challenge;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TwoStackTest {

    private TwoStack twoStack;

    @BeforeEach
    void setUp() {
        twoStack = new TwoStack(5);
    }

    @Test
    void testPushAndPopStack1() {
        twoStack.push1(10);
        twoStack.push1(20);
        assertEquals(20, twoStack.pop1());
        assertEquals(10, twoStack.pop1());
        assertTrue(twoStack.isEmpty1());
    }

    @Test
    void testPushAndPopStack2() {
        twoStack.push2(30);
        twoStack.push2(40);
        assertEquals(40, twoStack.pop2());
        assertEquals(30, twoStack.pop2());
        assertTrue(twoStack.isEmpty2());
    }

    @Test
    void testStack1IsEmpty() {
        assertTrue(twoStack.isEmpty1());
        twoStack.push1(5);
        assertFalse(twoStack.isEmpty1());
    }

    @Test
    void testStack2IsEmpty() {
        assertTrue(twoStack.isEmpty2());
        twoStack.push2(15);
        assertFalse(twoStack.isEmpty2());
    }

    @Test
    void testStack1IsFull() {
        twoStack.push1(1);
        twoStack.push1(2);
        twoStack.push1(3);
        twoStack.push2(5);
        assertFalse(twoStack.isFull1());
        assertFalse(twoStack.isFull2());
        twoStack.push1(4);
        assertTrue(twoStack.isFull1());
    }

    @Test
    void testStack2IsFull() {
        twoStack.push2(5);
        twoStack.push2(4);
        twoStack.push2(3);
        twoStack.push1(1);
        assertFalse(twoStack.isFull1());
        assertFalse(twoStack.isFull2());
        twoStack.push2(2);
        assertTrue(twoStack.isFull1());
        assertTrue(twoStack.isFull2());
    }

    @Test
    void testPush1WhenFullThrowsException() {
        twoStack.push1(1);
        twoStack.push1(2);
        twoStack.push1(3);
        twoStack.push2(4);
        twoStack.push2(5);
        assertThrows(IllegalStateException.class, () -> twoStack.push1(99));
    }

}
