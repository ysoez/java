package dsa.challenge;

import dsa.Node;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static dsa.challenge.LoopDetector.LINKED_LIST_LOOP_DETECTOR;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class LoopDetectorTest {

    @Nested
    class LinkedListLoopDetectorTest {
        @Test
        void testDetectCycle() {
            var node1 = new Node<>(10, null);
            var node2 = new Node<>(20, null);
            node1.next = node2;
            var node3 = new Node<>(30, null);
            node2.next = node3;
            var node4 = new Node<>(40, null);
            node3.next = node4;
            var node5 = new Node<>(50, null);
            node4.next = node5;
            node5.next = node3;

            assertTrue(LINKED_LIST_LOOP_DETECTOR.hasCycle(node1));
        }
        @Test
        void testNoCycle() {
            var head = new Node<>(10, new Node<>(20, new Node<>(30, null)));
            assertFalse(LINKED_LIST_LOOP_DETECTOR.hasCycle(head));
        }
    }

}