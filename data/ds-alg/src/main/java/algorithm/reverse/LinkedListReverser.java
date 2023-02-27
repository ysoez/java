package algorithm.reverse;

import data_structure.linked_list.UnaryNode;
import util.Complexity;

public class LinkedListReverser<E> implements Reverser<UnaryNode<E>> {

    @Override
    @Complexity(runtime = "O(n)", space = "O(1)")
    public UnaryNode<E> reverse(UnaryNode<E> node) {
        if (node == null)
            throw new IllegalArgumentException("Head is null");
        if (node.next() == null)
            return node;
        var prev = node;
        var current = node.next();
        while (current != null) {
            var next = current.next();
            current.next(prev);
            prev = current;
            current = next;
        }
        return prev;
    }

}
