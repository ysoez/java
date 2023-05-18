package algorithm;

import util.Algorithm;
import util.Algorithm.Complexity;
import util.Pair;
import util.UnaryNode;

import static util.Algorithm.Complexity.Value.CONSTANT;
import static util.Algorithm.Complexity.Value.LINEAR;

class LinkedLists {

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
