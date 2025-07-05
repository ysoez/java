package data_structure;

import java.util.HashMap;
import java.util.HashSet;

class Characters {

    static Character firstUniqueChar(String str) {
        if (str == null)
            throw new IllegalArgumentException();
        var wordsCount = new HashMap<Character, Integer>();
        var chars = str.toCharArray();
        for (char ch : chars) {
            var count = wordsCount.getOrDefault(ch, 0);
            wordsCount.put(ch, count + 1);
        }
        for (char ch : chars)
            if (wordsCount.get(ch) == 1)
                return ch;
        return Character.MIN_VALUE;
    }

    static Character firstRepeatedChar(String str) {
        if (str == null)
            throw new IllegalArgumentException();
        var unique = new HashSet<Character>();
        for (char ch : str.toCharArray()) {
            if (!unique.add(ch))
                return ch;
        }
        return Character.MIN_VALUE;
    }

}
