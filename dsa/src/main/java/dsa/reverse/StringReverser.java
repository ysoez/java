package dsa.reverse;

import dsa.Algorithm;

import java.util.ArrayDeque;

import static dsa.Algorithm.Complexity.LINEAR;

class StringReverser implements Reverser<String> {

    @Override
    @Algorithm(complexity = @Algorithm.Complexity(runtime = LINEAR, space = LINEAR))
    public String apply(String str) {
        if (str == null)
            return null;
        if (str.isBlank())
            return str;
        var stack = new ArrayDeque<Character>(str.length());
        for (char ch : str.toCharArray())
            stack.push(ch);
        var builder = new StringBuilder(str.length());
        while (!stack.isEmpty())
            builder.append(stack.pop());
        return builder.toString();
    }

}
