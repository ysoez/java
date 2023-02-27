package algorithm.search;

import java.util.HashMap;

class FirstUniqueCharFinder implements Finder<String, Character> {

    @Override
    public Character search(String input) {
        var charCount = new HashMap<Character, Integer>();
        char[] charArray = input.toCharArray();
        for (var ch : charArray)
            charCount.put(ch, charCount.getOrDefault(ch, 0) + 1);
        for (var ch : charArray)
            if (charCount.get(ch) == 1)
                return ch;
        return Character.MIN_VALUE;
    }

}
