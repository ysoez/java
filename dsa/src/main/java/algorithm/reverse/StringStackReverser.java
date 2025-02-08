package algorithm.reverse;

import data_structure.Algorithm;
import data_structure.Algorithm.Complexity;

import java.util.Stack;

import static data_structure.Algorithm.Complexity.Value.LINEAR;

class StringStackReverser implements Reverser<String> {

    @Override
    @Algorithm(complexity = @Complexity(runtime = LINEAR, space = LINEAR))
    public String apply(String str) {
        if (str == null)
            throw new IllegalArgumentException();
        var stack = new Stack<Character>();
        for (char c : str.toCharArray())
            stack.push(c);
        var builder = new StringBuilder();
        while (!stack.isEmpty())
            builder.append(stack.pop());
        return builder.toString();
    }

}
