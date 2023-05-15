package algorithm;

import util.Algorithm;
import util.Algorithm.Complexity;
import util.BinaryNode;

import static util.Algorithm.Complexity.Value.CONSTANT;
import static util.Algorithm.Complexity.Value.LINEAR;
import static util.Algorithm.Implementation.RECURSIVE;

class Trees {

    @Algorithm(
            complexity = @Complexity(runtime = LINEAR, space = CONSTANT),
            implementation = RECURSIVE
    )
    static boolean isBst(BinaryNode<Integer> head) {
        return isBst(head, null, null);
    }

    private static boolean isBst(BinaryNode<Integer> node, Integer lowerLim, Integer upperLim) {
        if (lowerLim != null && node.value < lowerLim)
            return false;
        if (upperLim != null && upperLim < node.value)
            return false;
        boolean isLeftBST = true;
        boolean isRightBST = true;
        if (node.left != null) {
            isLeftBST = isBst(node.left, lowerLim, node.value);
        }
        if (isLeftBST && node.right != null) {
            isRightBST = isBst(node.right, node.value, upperLim);
        }
        return isLeftBST && isRightBST;
    }

}
