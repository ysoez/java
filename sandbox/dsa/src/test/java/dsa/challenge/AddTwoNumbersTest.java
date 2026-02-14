package dsa.challenge;

import dsa.Node;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AddTwoNumbersTest {

    @Test
    void test1() {
        var node1 = new Node<>(2, new Node<>(4, new Node<>(3))); // 342
        var node2 = new Node<>(5, new Node<>(6, new Node<>(4))); // 465
        var result = new Node<>(7, new Node<>(0, new Node<>(8))); // 807
        assertEquals(result, AddTwoNumbers.Default.INSTANCE.apply(node1, node2));
    }

    @Test
    void test2() {
        var node1 = new Node<>(0);
        var node2 = new Node<>(0);
        var result = new Node<>(0);
        assertEquals(result, AddTwoNumbers.Default.INSTANCE.apply(node1, node2));
    }

    @Test
    void test3() {
        var node1 = new Node<>(9);
        var current = node1;
        for (int i = 0; i < 6; i++) {
            current.next = new Node<>(9);
            current = current.next;
        }
        var node2 = new Node<>(9,
                new Node<>(9,
                        new Node<>(9,
                                new Node<>(9))));
        var result = new Node<>(8,
                new Node<>(9,
                        new Node<>(9,
                                new Node<>(9,
                                        new Node<>(0,
                                                new Node<>(0,
                                                        new Node<>(0,
                                                                new Node<>(1))))))));
        assertEquals(result, AddTwoNumbers.Default.INSTANCE.apply(node1, node2));
    }
}