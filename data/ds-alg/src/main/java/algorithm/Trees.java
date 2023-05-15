package algorithm;

import util.Algorithm;
import util.Algorithm.Complexity;
import util.BinaryNode;

import java.util.Stack;

import static util.Algorithm.Complexity.Value.CONSTANT;
import static util.Algorithm.Complexity.Value.LINEAR;
import static util.Algorithm.Complexity.Value.LOGARITHMIC;
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

    @Algorithm(complexity = @Complexity(runtime = LINEAR, space = LOGARITHMIC))
    static Integer lca(BinaryNode<Integer> root, int j, int k) {
        var pathToJ = pathToX(root, j);
        var pathToK = pathToX(root, k);
        if (pathToJ == null || pathToK == null) {
            return null;
        }
        BinaryNode<Integer> lca = null;
        while (!pathToJ.isEmpty() && !pathToK.isEmpty()) {
            var jPop = pathToJ.pop();
            var kPop = pathToK.pop();
            if (jPop == kPop) {
                lca = jPop;
            } else {
                break;
            }
        }
        return lca != null ? lca.value : null;
    }

    private static Stack<BinaryNode<Integer>> pathToX(BinaryNode<Integer> node, int x) {
        if (node == null) {
            return null;
        }
        if (node.value == x) {
            var path = new Stack<BinaryNode<Integer>>();
            path.push(node);
            return path;
        }
        var leftPath = pathToX(node.left, x);
        if (leftPath != null) {
            leftPath.push(node);
            return leftPath;
        }
        var rightPath = pathToX(node.right, x);
        if (rightPath != null) {
            rightPath.push(node);
            return rightPath;
        }
        return null;
    }

}
