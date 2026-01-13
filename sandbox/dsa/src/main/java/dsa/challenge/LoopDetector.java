package dsa.challenge;

import dsa.Node;

interface LoopDetector<T> {

    boolean hasCycle(Node<T> node);

    LinkedListLoopDetector LINKED_LIST_LOOP_DETECTOR = new LinkedListLoopDetector();

    class LinkedListLoopDetector implements LoopDetector<Integer> {
        @Override
        public boolean hasCycle(Node<Integer> node) {
            var slow = node;
            var fast = node;
            while (fast != null && fast.next != null) {
                slow = slow.next;
                fast = fast.next.next;
                if (slow == fast)
                    return true;
            }
            return false;
        }
    }

}
