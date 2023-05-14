package algorithm;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

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

//    @Test
//    void countVowels() {
//        assertEquals(0, vowelsCount(null));
//        assertEquals(0, vowelsCount(EMPTY));
//        assertEquals(0, vowelsCount("   "));
//        assertEquals(3, vowelsCount("Hello World"));
//        assertEquals(3, vowelsCount("HellO World"));
//    }
//
//    @Test
//    void reverseString() {
//        assertEquals(EMPTY, reverse(null));
//        assertEquals(EMPTY, reverse(EMPTY));
//        assertEquals(SPACE, reverse(SPACE));
//        assertEquals("der", reverse("red"));
//        assertEquals("DER", reverse("RED"));
//    }
//
//    @Test
//    void reverseWordsInString() {
//        assertEquals(EMPTY, reverseWords(null));
//        assertEquals(EMPTY, reverseWords(EMPTY));
//        assertEquals(EMPTY, reverseWords(SPACE));
//        assertEquals(EMPTY, reverseWords("    "));
//        assertEquals("Trees", reverseWords("Trees"));
//        assertEquals("beautiful are Trees", reverseWords("Trees are beautiful"));
//        assertEquals("beautiful are Trees", reverseWords("Trees are beautiful "));
//        assertEquals("beautiful are Trees", reverseWords("  Trees are beautiful "));
//    }
//
//    @Test
//    void rotations() {
//        assertFalse(areRotations("ABCD", null));
//        assertFalse(areRotations("ABCD", SPACE));
//        assertFalse(areRotations("ABCD", EMPTY));
//        assertFalse(areRotations("ABCD", "DABC1"));
//        assertFalse(areRotations("ABCD", "DACB"));
//
//        assertTrue(areRotations("ABCD", "DABC"));
//        assertTrue(areRotations("ABCD", "CDAB"));
//        assertTrue(areRotations("ABCD", "BCDA"));
//        assertTrue(areRotations("ABCD", "ABCD"));
//    }
//
//    @Test
//    void removeDuplicateChars() {
//        assertEquals(EMPTY, removeDuplicates(null));
//        assertEquals(EMPTY, removeDuplicates(EMPTY));
//        assertEquals(SPACE, removeDuplicates("  "));
//        assertEquals("Tres abutifl", removeDuplicates("Trees are beautiful"));
//    }
//
//    @Test
//    void mostRepeatedChar() {
//        assertThrows(IllegalArgumentException.class, () -> maxOccuringChar(null));
//        assertThrows(IllegalArgumentException.class, () -> maxOccuringChar(EMPTY));
//        assertEquals('e', maxOccuringChar("Trees are beautiful"));
//    }
//
//    @Test
//    void capitalizeEachWord() {
//        assertEquals(EMPTY, capitalize(null));
//        assertEquals(EMPTY, capitalize(EMPTY));
//
//        assertEquals(EMPTY, capitalize("   "));
//        assertEquals("Today Is A Great Day", capitalize("    TODAY IS A GREAT DAY"));
//        assertEquals("Today Is A Great Day", capitalize("    TODAY IS A GREAT DAY   "));
//        assertEquals("Today Is A Great Day", capitalize("    TODAY   IS A    GREAT DAY   "));
//
//        assertEquals("Today Is A Great Day", capitalize("today is a great day"));
//        assertEquals("Today Is A Great Day", capitalize("TODAY IS A GREAT DAY"));
//    }
//
//    @Test
//    void areAnagramStrings() {
//        assertFalse(areAnagrams("ABCD", null));
//        assertFalse(areAnagrams("ABCD", EMPTY));
//        assertFalse(areAnagrams("ABCD", SPACE));
//
//        assertFalse(areAnagrams("ABCD1", "DBCA"));
//        assertFalse(areAnagrams("ABCD", "DBCA1"));
//        assertFalse(areAnagrams("ABCX", "DBCA"));
//
//        assertTrue(areAnagrams("ABCD", "DBCA"));
//        assertTrue(areAnagrams("ABCd", "DBCA"));
//
////        assertFalse(areAnagramsHistogram("ABCD", null));
////        assertFalse(areAnagramsHistogram("ABCD", EMPTY));
////        assertFalse(areAnagramsHistogram("ABCD", SPACE));
//
////        assertFalse(areAnagramsHistogram("ABCD1", "DBCA"));
////        assertFalse(areAnagramsHistogram("ABCD", "DBCA1"));
////        assertFalse(areAnagramsHistogram("ABCX", "DBCA"));
////
////        assertTrue(areAnagramsHistogram("ABCD", "DBCA"));
////        assertTrue(areAnagramsHistogram("ABCd", "DBCA"));
//    }
//
//    @Test
//    void isPalindromeString() {
//        assertFalse(isPalindrome(null));
//        assertFalse(isPalindrome("ABBC"));
//        assertFalse(isPalindrome("ABBa"));
//
//        assertTrue(isPalindrome(EMPTY));
//        assertTrue(isPalindrome("ABBA"));
//    }

}