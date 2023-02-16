package algorithm;

import data_structure.linked_list.UnaryNode;
import util.Complexity;

public class Reverser {

    public static int[] reverse(int[] arr) {
        var reversedArr = new int[arr.length];
        for (int i = arr.length - 1, j = 0; i >= 0; i--, j++) {
            reversedArr[j] = arr[i];
        }
        return reversedArr;
    }

    @Complexity(runtime = "O(n)", space = "O(1)")
    public static <E> UnaryNode<E> reverse(UnaryNode<E> node) {
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
