package algorithm.search;

import util.Complexity;

import java.util.HashSet;

class FirstRepeatedCharFinder implements Finder<String, Character> {

    @Override
    @Complexity(runtime = "O(n)", space = "O(n)")
    public Character search(String input) {
        var charSet = new HashSet<Character>();
        for (var ch : input.toCharArray())
            if (!charSet.add(ch))
                return ch;
        return Character.MIN_VALUE;
    }

}
