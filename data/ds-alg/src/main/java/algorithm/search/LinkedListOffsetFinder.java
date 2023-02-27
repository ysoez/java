package algorithm.search;

import data_structure.linked_list.UnaryNode;
import util.Complexity;

class LinkedListOffsetFinder<E> implements OffsetFinder<UnaryNode<E>, UnaryNode<E>> {

    @Override
    @Complexity(runtime = "O(n)", space = "O(1)")
    public UnaryNode<E> search(UnaryNode<E> node, int endOffset) {
        if (node == null)
            throw new IllegalArgumentException("Nullable list head");
        var slow = node;
        var fast = node;
        for (int i = 0; i < endOffset - 1; i++) {
            fast = fast.next();
            if (fast == null)
                throw new IllegalArgumentException("Offset greater than size");
        }
        while (fast.next() != null) {
            slow = slow.next();
            fast = fast.next();
        }
        return slow;
    }

}
