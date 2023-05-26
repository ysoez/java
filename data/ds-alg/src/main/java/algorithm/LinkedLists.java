package algorithm;

import util.Algorithm;
import util.Algorithm.Complexity;
import util.Pair;
import util.UnaryNode;

import static util.Algorithm.Complexity.Value.CONSTANT;
import static util.Algorithm.Complexity.Value.LINEAR;
import static util.Algorithm.Target.IN_PLACE;

public class LinkedLists {

    @Algorithm(
            complexity = @Complexity(runtime = LINEAR, space = CONSTANT),
            target = IN_PLACE
    )
    public static <T> UnaryNode<T> reverse(UnaryNode<T> node) {
        if (node == null)
            throw new IllegalArgumentException("Head is null");
        if (node.next == null)
            return node;
        var prev = node;
        var cur = node.next;
        while (cur != null) {
            var next = cur.next;
            cur.next = prev;
            prev = cur;
            cur = next;
        }
        return prev;
    }

    static boolean hasCycle(UnaryNode<Integer> head) {
        return false;
    }

    static Pair<Integer, Integer> middle(UnaryNode<Integer> node) {
        if (node == null)
            throw new IllegalArgumentException("Nullable list head");
        // 10 20
        return new Pair<>(null, null);
    }

    @Algorithm(complexity = @Complexity(runtime = LINEAR, space = CONSTANT))
    static UnaryNode<Integer> nthFromLast(UnaryNode<Integer> head, int n) {
        var left = head;
        var right = head;
        for (int i = 0; i < n; i++) {
            if (right == null)
                return null;
            right = right.next;
        }
        while (right != null) {
            left = left.next;
            right = right.next;
        }
        return left;
    }

}
