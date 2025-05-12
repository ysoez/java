package dsa.reverse.string;

import dsa.Algorithm.Complexity;
import dsa.reverse.Reverser;

import static dsa.Algorithm.Complexity.Value.LINEAR;

class ArrayStringReverser implements Reverser<String> {

    @Override
    @Complexity(runtime = LINEAR, space = LINEAR)
    public String apply(String str) {
        if (str == null)
            throw new IllegalArgumentException();
        if (str.isBlank() || str.length() < 2)
            return str;
        var chars = str.toCharArray();
        var reversed = new char[chars.length];
        for (int i = chars.length - 1, j = 0; i >= 0; i--, j++)
            reversed[j] = chars[i];
        return new String(reversed);
    }

}
