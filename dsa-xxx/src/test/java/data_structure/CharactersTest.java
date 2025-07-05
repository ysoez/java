package data_structure;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CharactersTest {

    @Test
    void firstUniqueChar() {
        assertEquals('l', Characters.firstUniqueChar("leetcode"));
        assertEquals('v', Characters.firstUniqueChar("loveleetcode"));
        assertEquals('c', Characters.firstUniqueChar("abcab"));
        assertEquals('c', Characters.firstUniqueChar("aabbbc"));
        assertEquals('d', Characters.firstUniqueChar("aabbdbc"));

        assertThrows(IllegalArgumentException.class, () -> Characters.firstUniqueChar(null));
        assertEquals(Character.MIN_VALUE, Characters.firstUniqueChar(""));
        assertEquals(Character.MIN_VALUE, Characters.firstUniqueChar("aabb"));
    }

    @Test
    void firstRepeatedChar() {
        assertEquals('e', Characters.firstRepeatedChar("a green apple"));
        assertEquals('e', Characters.firstRepeatedChar("geeksforgeeks"));

        assertEquals(Character.MIN_VALUE, Characters.firstRepeatedChar("abcde"));
    }


}