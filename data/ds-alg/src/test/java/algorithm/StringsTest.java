package algorithm;

import org.junit.jupiter.api.Test;

import static algorithm.Strings.EMPTY;
import static algorithm.Strings.SPACE;
import static algorithm.Strings.areAnagrams;
import static algorithm.Strings.areAnagramsHistogram;
import static algorithm.Strings.areRotations;
import static algorithm.Strings.capitalize;
import static algorithm.Strings.isPalindrome;
import static algorithm.Strings.reverseWords;
import static org.junit.jupiter.api.Assertions.*;

class StringsTest {

    @Test
    void firstUniqueChar() {
        assertNull(Strings.firstUniqueChar("aabb"));
        assertEquals('l', Strings.firstUniqueChar("leetcode"));
        assertEquals('v', Strings.firstUniqueChar("loveleetcode"));
        assertEquals('c', Strings.firstUniqueChar("abcab"));
        assertEquals('c', Strings.firstUniqueChar("aabbbc"));
        assertEquals('d', Strings.firstUniqueChar("aabbdbc"));
    }

    @Test
    void firstRepeatedChar() {
        assertEquals('e', Strings.firstRepeatedChar("a green apple"));
        assertEquals('e', Strings.firstRepeatedChar("geeksforgeeks"));
        assertEquals(Character.MIN_VALUE, Strings.firstRepeatedChar("abcde"));
    }

    @Test
    void isOneWay() {
        assertTrue(Strings.isOneAway("abcde", "abcd"));
        assertTrue(Strings.isOneAway("abde", "abcde"));
        assertTrue(Strings.isOneAway("a", "a"));
        assertTrue(Strings.isOneAway("abcdef", "abqdef"));
        assertTrue(Strings.isOneAway("abcdef", "abccef"));
        assertTrue(Strings.isOneAway("abcdef", "abcde"));

        assertFalse(Strings.isOneAway("aaa", "abc"));
        assertFalse(Strings.isOneAway("abcde", "abc"));
        assertFalse(Strings.isOneAway("abc", "abcde"));
        assertFalse(Strings.isOneAway("abc", "bcc"));
    }

    @Test
    void countVowels() {
        assertEquals(0, Strings.vowelsCount(null));
        assertEquals(0, Strings.vowelsCount(EMPTY));
        assertEquals(0, Strings.vowelsCount("   "));
        assertEquals(3, Strings.vowelsCount("Hello World"));
    }

    @Test
    void rotations() {
        assertFalse(areRotations("ABCD", null));
        assertFalse(areRotations("ABCD", SPACE));
        assertFalse(areRotations("ABCD", EMPTY));
        assertFalse(areRotations("ABCD", "DABC1"));
        assertFalse(areRotations("ABCD", "DACB"));

        assertTrue(areRotations("ABCD", "DABC"));
        assertTrue(areRotations("ABCD", "CDAB"));
        assertTrue(areRotations("ABCD", "BCDA"));
        assertTrue(areRotations("ABCD", "ABCD"));
    }

    @Test
    void mostRepeatedChar() {
        assertThrows(IllegalArgumentException.class, () -> Strings.mostRepeatedChar(null));
        assertThrows(IllegalArgumentException.class, () -> Strings.mostRepeatedChar(EMPTY));
        assertEquals('e', Strings.mostRepeatedChar("Trees are beautiful"));
    }

    @Test
    void removeDuplicateChars() {
        assertEquals(EMPTY, Strings.removeDuplicates(null));
        assertEquals(EMPTY, Strings.removeDuplicates(EMPTY));
        assertEquals(SPACE, Strings.removeDuplicates("  "));
        assertEquals("Tres abutifl", Strings.removeDuplicates("Trees are beautiful"));
    }

    @Test
    void capitalizeEachWord() {
        assertEquals(EMPTY, capitalize(null));
        assertEquals(EMPTY, capitalize(EMPTY));

        assertEquals(EMPTY, capitalize("   "));
        assertEquals("Today Is A Great Day", capitalize("    TODAY IS A GREAT DAY"));
        assertEquals("Today Is A Great Day", capitalize("    TODAY IS A GREAT DAY   "));
        assertEquals("Today Is A Great Day", capitalize("    TODAY   IS A    GREAT DAY   "));

        assertEquals("Today Is A Great Day", capitalize("today is a great day"));
        assertEquals("Today Is A Great Day", capitalize("TODAY IS A GREAT DAY"));
    }

    @Test
    void reverse() {
        assertNull(Strings.reverse((null)));
        assertEquals(EMPTY, Strings.reverse(EMPTY));
        assertEquals(SPACE, Strings.reverse(SPACE));
        assertEquals("der", Strings.reverse("red"));
        assertEquals("DER", Strings.reverse("RED"));
    }

    @Test
    void reverseWordsInString() {
        assertNull(reverseWords(null));
        assertEquals(EMPTY, reverseWords(EMPTY));
        assertEquals(EMPTY, reverseWords(SPACE));
        assertEquals(EMPTY, reverseWords("    "));
        assertEquals("Trees", reverseWords("Trees"));
        assertEquals("beautiful are Trees", reverseWords("Trees are beautiful"));
        assertEquals("beautiful are Trees", reverseWords("Trees are beautiful "));
        assertEquals("beautiful are Trees", reverseWords("  Trees are beautiful "));
    }

    @Test
    void areAnagramStrings() {
        assertFalse(areAnagrams("ABCD", null));
        assertFalse(areAnagrams("ABCD", EMPTY));
        assertFalse(areAnagrams("ABCD", SPACE));

        assertFalse(areAnagrams("ABCD1", "DBCA"));
        assertFalse(areAnagrams("ABCD", "DBCA1"));
        assertFalse(areAnagrams("ABCX", "DBCA"));

        assertTrue(areAnagrams("ABCD", "DBCA"));
        assertTrue(areAnagrams("ABCd", "DBCA"));

        assertFalse(areAnagramsHistogram("ABCD", null));
        assertFalse(areAnagramsHistogram("ABCD", EMPTY));
        assertFalse(areAnagramsHistogram("ABCD", SPACE));

        assertFalse(areAnagramsHistogram("ABCD1", "DBCA"));
        assertFalse(areAnagramsHistogram("ABCD", "DBCA1"));
        assertFalse(areAnagramsHistogram("ABCX", "DBCA"));

        assertTrue(areAnagramsHistogram("ABCD", "DBCA"));
        assertTrue(areAnagramsHistogram("ABCd", "DBCA"));
    }

    @Test
    void isPalindromeString() {
        assertFalse(isPalindrome(null));
        assertFalse(isPalindrome("ABBC"));
        assertFalse(isPalindrome("ABBa"));

        assertTrue(isPalindrome(EMPTY));
        assertTrue(isPalindrome("ABBA"));
    }

    @Test
    void longestSubstringLength() {
        assertEquals(1, Strings.longestSubstringLength(" "));
        assertEquals(2, Strings.longestSubstringLength("au"));
        assertEquals(2, Strings.longestSubstringLength("aab"));
        assertEquals(3, Strings.longestSubstringLength("dvdf"));
        assertEquals(3, Strings.longestSubstringLength("abcabcbb"));
        assertEquals(1, Strings.longestSubstringLength("bbbbb"));
        assertEquals(3, Strings.longestSubstringLength("pwwkew"));
    }

    @Test
    void arrowRotateCount() {
        assertEquals(2, Strings.arrowRotateCount("^vv<v"));
        assertEquals(3, Strings.arrowRotateCount("v>>>vv"));
        assertEquals(0, Strings.arrowRotateCount("<<<"));
    }

    @Test
    void aOccursBeforeB() {
        assertTrue(Strings.aOccursBeforeB("aabbb"));
        assertFalse(Strings.aOccursBeforeB("ba"));
        assertTrue(Strings.aOccursBeforeB("aaa"));
        assertTrue(Strings.aOccursBeforeB("b"));
        assertFalse(Strings.aOccursBeforeB("abba"));
    }

}