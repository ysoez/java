package algorithm;

import util.Algorithm;

import java.util.LinkedHashMap;

import static java.lang.Math.abs;
import static util.Algorithm.Complexity.Value.CONSTANT;
import static util.Algorithm.Complexity.Value.LINEAR;

public class Strings {

    static final String EMPTY = "";
    static final String SPACE = " ";
    static final int ASCII_SIZE = 256;
    static final int ENGLISH_ALPHABET = 26;

    @Algorithm(complexity = @Algorithm.Complexity(runtime = LINEAR, space = LINEAR))
    static Character firstUniqueChar(String str) {
        var frequency = new LinkedHashMap<Character, Integer>();
        for (char c : str.toCharArray())
            frequency.put(c, frequency.getOrDefault(c, 0) + 1);
        for (var entry : frequency.entrySet())
            if (entry.getValue() == 1)
                return entry.getKey();
        return null;
    }

    @Algorithm(complexity = @Algorithm.Complexity(runtime = LINEAR, space = CONSTANT))
    static boolean isOneAway(String first, String second) {
        if (first == null || second == null)
            return false;
        if (abs(first.length() - second.length()) > 1)
            return false;
        if (first.length() == second.length()) {
            int diffCount = 0;
            for (int i = 0; i < first.length(); i++)
                if (first.charAt(i) != second.charAt(i)) {
                    diffCount++;
                    if (diffCount > 1)
                        return false;
                }
            return true;
        }
        int i = 0;
        int diffCount = 0;
        String longerStr = first.length() > second.length() ? first : second;
        String shorterStr = first.length() < second.length() ? first : second;
        while (i < shorterStr.length()) {
            if (longerStr.charAt(i + diffCount) == shorterStr.charAt(i)) {
                i++;
            } else {
                diffCount++;
            }
            if (diffCount > 1)
                return false;
        }
        return true;
    }



//    static int vowelsCount(String str) {
//        if (str == null)
//            return 0;
//        var count = 0;
//        String vowels = "aeiou";
//        for (char ch : str.toLowerCase().toCharArray())
//            if (vowels.indexOf(ch) != -1)
//                count++;
//        return count;
//    }

//    static String reverseStack(String str) {
//        var stack = new Stack<Character>();
//        for (char ch : str.toCharArray())
//            stack.push(ch);
//        var builder = new StringBuilder();
//        while (!stack.isEmpty())
//            builder.append(stack.pop());
//        return builder.toString();
//    }

//    static String reverse(String str) {
//        if (str == null)
//            return EMPTY;
//        var reversed = new StringBuilder();
//        char[] chars = str.toCharArray();
//        for (int i = chars.length - 1; i >= 0; i--)
//            reversed.append(chars[i]);
//        return reversed.toString();
//    }

    //    static String reverseWords(String str) {
//        if (str == null)
//            return EMPTY;
//        var reversed = new StringBuilder();
//        String[] words = str.split(SPACE);
//        for (int i = words.length - 1; i >= 0; i--)
//            reversed.append(words[i]).append(SPACE);
//        return reversed.toString().trim();
//    }

//    static String reverseWords(String sentence) {
//        if (sentence == null)
//            return EMPTY;
//        String[] words = sentence.trim().split(SPACE);
//        Collections.reverse(asList(words));
//        return String.join(SPACE, words);
//    }
//
//    static boolean areRotations(String str1, String str2) {
//        if (str1 == null || str2 == null)
//            return false;
//        return str1.length() == str2.length() && (str1 + str1).contains(str2);
//    }
//
//    static String removeDuplicates(String str) {
//        if (str == null)
//            return EMPTY;
//        var output = new StringBuilder();
//        var set = new HashSet<Character>();
//        for (char ch : str.toCharArray())
//            if (set.add(ch))
//                output.append(ch);
//        return output.toString();
//    }

//    static char maxOccuringChar(String str) {
//
//        Map<Character, Integer> frequencies = new HashMap<>();
//        for (char ch : str.toCharArray()) {
//            if (frequencies.containsKey(ch))
//                frequencies.replace(ch, frequencies.get(ch) + 1);
//            else
//                frequencies.put(ch, 1);
//        }
//
//    }

//    static char maxOccuringChar(String str) {
//        if (str == null || str.isEmpty())
//            throw new IllegalArgumentException();
//        int[] frequencies = new int[ASCII_SIZE];
//        for (char ch : str.toCharArray())
//            frequencies[ch]++;
//        int max = 0;
//        char result = ' ';
//        for (int i = 0; i < frequencies.length; i++)
//            if (frequencies[i] > max){
//                max = frequencies[i];
//                result = (char) i;
//            }
//        return result;
//    }
//
//    static String capitalize(String sentence) {
//        if (sentence == null || sentence.trim().isEmpty())
//            return EMPTY;
//        String[] words = sentence.trim().replaceAll(" +", SPACE).split(SPACE);
//        for (int i = 0; i < words.length; i++)
//            words[i] = words[i].substring(0, 1).toUpperCase() + words[i].substring(1).toLowerCase();
//        return String.join(SPACE, words);
//    }
//
//    // n logn
//    static boolean areAnagrams(String str1, String str2) {
//        if (str1 == null || str2 == null || str1.length() != str2.length())
//            return false;
//        var array1 = str1.toLowerCase().toCharArray();
//        sort(array1);
//        var array2 = str2.toLowerCase().toCharArray();
//        sort(array2);
//        return java.util.Arrays.equals(array1, array2);
//    }
//
//    // o(n)
//    static boolean areAnagramsHistogram(String str1, String str2) {
//        if (str1 == null || str2 == null)
//            return false;
//        int[] frequencies = new int[ENGLISH_ALPHABET];
//        str1 = str1.toLowerCase();
//        for (int i = 0; i < str1.length(); i++)
//            frequencies[str1.charAt(i) - 'a']++;
//        str2 = str2.toLowerCase();
//        for (int i = 0; i < str2.length(); i++) {
//            int index = str2.charAt(i) - 'a';
//            if (frequencies[index] == 0)
//                return false;
//            frequencies[index]--;
//        }
//        return true;
//    }

    // 4 times iteration
//    static boolean isPalindrome(String word) {
//        var reversed = new StringBuilder(word);
//        reversed.reverse();
//        return reversed.toString().equals(word);
//    }

    // 0.5 iteration
//    static boolean isPalindrome(String word) {
//        if (word == null)
//            return false;
//        int left = 0;
//        int right = word.length() - 1;
//        while (left < right)
//            if (word.charAt(left++) != word.charAt(right--))
//                return false;
//        return true;
//    }

}
