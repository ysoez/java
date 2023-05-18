package algorithm;

import util.Algorithm;
import util.Algorithm.Complexity;

import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashMap;

import static java.lang.Math.abs;
import static java.util.Arrays.asList;
import static java.util.Arrays.sort;
import static util.Algorithm.Complexity.Value.CONSTANT;
import static util.Algorithm.Complexity.Value.LINEAR;
import static util.Algorithm.Complexity.Value.N_LOG_N;
import static util.Algorithm.Complexity.Value.POLYNOMIAL;

class Strings {

    static final String EMPTY = "";
    static final String SPACE = " ";
    static final int ASCII_SIZE = 256;
    static final int ENGLISH_ALPHABET = 26;

    @Algorithm(complexity = @Complexity(runtime = LINEAR, space = LINEAR))
    static Character firstUniqueChar(String str) {
        var frequency = new LinkedHashMap<Character, Integer>();
        for (char c : str.toCharArray())
            frequency.put(c, frequency.getOrDefault(c, 0) + 1);
        for (var entry : frequency.entrySet())
            if (entry.getValue() == 1)
                return entry.getKey();
        return null;
    }

    @Algorithm(complexity = @Complexity(runtime = LINEAR, space = LINEAR))
    static Character firstRepeatedChar(String input) {
        var charSet = new HashSet<Character>();
        for (var ch : input.toCharArray())
            if (!charSet.add(ch))
                return ch;
        return Character.MIN_VALUE;
    }

    @Algorithm(complexity = @Complexity(runtime = LINEAR, space = CONSTANT))
    static char mostRepeatedChar(String str) {
        if (str == null || str.isEmpty())
            throw new IllegalArgumentException();
        int[] frequencies = new int[ASCII_SIZE];
        for (char ch : str.toCharArray())
            frequencies[ch]++;
        int max = 0;
        char result = ' ';
        for (int i = 0; i < frequencies.length; i++)
            if (frequencies[i] > max) {
                max = frequencies[i];
                result = (char) i;
            }
        return result;
    }

    @Algorithm(complexity = @Complexity(runtime = LINEAR, space = CONSTANT))
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

    @Algorithm(complexity = @Complexity(runtime = LINEAR, space = CONSTANT))
    static int vowelsCount(String str) {
        if (str == null)
            return 0;
        var count = 0;
        String vowels = "aeiou";
        for (char ch : str.toLowerCase().toCharArray())
            if (vowels.indexOf(ch) != -1)
                count++;
        return count;
    }

    @Algorithm(complexity = @Complexity(runtime = LINEAR, space = LINEAR))
    static boolean areRotations(String str1, String str2) {
        if (str1 == null || str2 == null)
            return false;
        return str1.length() == str2.length() && (str1 + str1).contains(str2);
    }

    @Algorithm(complexity = @Complexity(runtime = LINEAR, space = POLYNOMIAL))
    static String removeDuplicates(String str) {
        if (str == null)
            return EMPTY;
        var output = new StringBuilder();
        var set = new HashSet<Character>();
        for (char ch : str.toCharArray())
            if (set.add(ch))
                output.append(ch);
        return output.toString();
    }

    @Algorithm(complexity = @Complexity(runtime = LINEAR, space = LINEAR))
    static String capitalize(String sentence) {
        if (sentence == null || sentence.trim().isEmpty())
            return EMPTY;
        String[] words = sentence.trim().replaceAll(" +", SPACE).split(SPACE);
        for (int i = 0; i < words.length; i++)
            words[i] = words[i].substring(0, 1).toUpperCase() + words[i].substring(1).toLowerCase();
        return String.join(SPACE, words);
    }

//    static String reverseStack(String str) {
//        var stack = new Stack<Character>();
//        for (char ch : str.toCharArray())
//            stack.push(ch);
//        var builder = new StringBuilder();
//        while (!stack.isEmpty())
//            builder.append(stack.pop());
//        return builder.toString();
//    }

    @Algorithm(complexity = @Complexity(runtime = LINEAR, space = LINEAR))
    static String reverse(String str) {
        if (str == null)
            return null;
        var reversed = new StringBuilder();
        char[] chars = str.toCharArray();
        for (int i = chars.length - 1; i >= 0; i--)
            reversed.append(chars[i]);
        return reversed.toString();
    }

    @Algorithm(complexity = @Complexity(runtime = LINEAR, space = LINEAR))
    static String reverseWords(String str) {
        if (str == null)
            return null;
        var reversed = new StringBuilder();
        String[] words = str.split(SPACE);
        for (int i = words.length - 1; i >= 0; i--)
            reversed.append(words[i]).append(SPACE);
        return reversed.toString().trim();
    }

    static String reverseSentence(String sentence) {
        if (sentence == null)
            return null;
        String[] words = sentence.trim().split(SPACE);
        Collections.reverse(asList(words));
        return String.join(SPACE, words);
    }

    @Algorithm(complexity = @Complexity(runtime = N_LOG_N, space = CONSTANT))
    static boolean areAnagrams(String str1, String str2) {
        if (str1 == null || str2 == null || str1.length() != str2.length())
            return false;
        var array1 = str1.toLowerCase().toCharArray();
        sort(array1);
        var array2 = str2.toLowerCase().toCharArray();
        sort(array2);
        return java.util.Arrays.equals(array1, array2);
    }

    @Algorithm(complexity = @Complexity(runtime = LINEAR, space = CONSTANT))
    static boolean areAnagramsHistogram(String str1, String str2) {
        if (str1 == null || str2 == null)
            return false;
        int[] frequencies = new int[ENGLISH_ALPHABET];
        str1 = str1.toLowerCase();
        for (int i = 0; i < str1.length(); i++)
            frequencies[str1.charAt(i) - 'a']++;
        str2 = str2.toLowerCase();
        for (int i = 0; i < str2.length(); i++) {
            int index = str2.charAt(i) - 'a';
            if (frequencies[index] == 0)
                return false;
            frequencies[index]--;
        }
        return true;
    }

    @Algorithm(complexity = @Complexity(runtime = LINEAR, space = CONSTANT))
    static boolean isPalindrome(String word) {
        if (word == null)
            return false;
        int left = 0;
        int right = word.length() - 1;
        while (left < right)
            if (word.charAt(left++) != word.charAt(right--))
                return false;
        return true;
    }

    // 4 times iteration
//    static boolean isPalindrome(String word) {
//        var reversed = new StringBuilder(word);
//        reversed.reverse();
//        return reversed.toString().equals(word);
//    }


    static int longestSubstringLength(String str) {
        // aab
        var longestSubstrLength = 0;
        var uniqueChars = new HashSet<Character>();
        for (int i = 0; i < str.length(); i++) {
            String subStr = "";
            boolean hasUniqueChars = false;
            for (int j = i; j < str.length(); j++) {
                char ch = str.charAt(j);
                subStr += ch;
                hasUniqueChars = uniqueChars.add(ch);
            }
            if (hasUniqueChars && subStr.length() > longestSubstrLength) {
                longestSubstrLength = subStr.length();
            }
            uniqueChars.clear();
        }
        return longestSubstrLength;
    }

}
