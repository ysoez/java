package dsa.challenge;

import dsa.Reverser;

import java.util.ArrayDeque;

class StringReverser implements Reverser<String> {

    @Override
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
