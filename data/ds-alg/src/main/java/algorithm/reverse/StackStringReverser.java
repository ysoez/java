package algorithm.reverse;

import util.Complexity;

import java.util.ArrayDeque;

class StackStringReverser implements Reverser<String> {

    @Complexity(runtime = "O(n)", space = "O(n)")
    public String reverse(String str) {
        var stack = new ArrayDeque<Character>();
        for (char ch : str.toCharArray())
            stack.push(ch);
        var stringBuilder = new StringBuilder();
        while (!stack.isEmpty())
            stringBuilder.append(stack.pop());
        return stringBuilder.toString();
    }

}
