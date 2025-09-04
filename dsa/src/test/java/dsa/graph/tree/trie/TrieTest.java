package dsa.graph.tree.trie;

import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

abstract class TrieTest {

    abstract Trie newTrie();

    @Test
    void testContains() {
        var trie = newTrie();
        trie.insert("cant");

        assertFalse(trie.contains("can"));
        assertTrue(trie.contains("cant"));
    }

    @Test
    void testContainsInvalidWord() {
        var trie = newTrie();
        trie.insert("cant");

        assertFalse(trie.contains(null));
        assertFalse(trie.contains(""));
    }

    @Test
    void testRemoveSoft() {
        var trie = newTrie();
        trie.insert("car");
        trie.insert("care");

        trie.remove("car");

        assertFalse(trie.contains("car"));
        assertTrue(trie.contains("care"));
    }

    @Test
    void testRemoveHard() {
        var trie = newTrie();
        trie.insert("car");
        trie.insert("care");

        trie.remove("care");

        assertTrue(trie.contains("car"));
        assertFalse(trie.contains("care"));
    }

    @Test
    void testRemoveNotExistedWord() {
        var trie = newTrie();
        trie.insert("car");

        trie.remove("book");

        assertTrue(trie.contains("car"));
    }

    @Test
    void testRemoveInvalidWord() {
        var trie = newTrie();
        trie.insert("car");

        trie.remove("");
        trie.remove(" ");
        trie.remove(null);

        assertTrue(trie.contains("car"));
    }

    @Test
    void testFindWords() {
        var trie = newTrie();
        trie.insert("car");
        trie.insert("card");
        trie.insert("care");
        trie.insert("careful");
        trie.insert("egg");

        assertEquals(List.of("car", "card", "care", "careful", "egg"), trie.findWords(""));

        assertEquals(List.of("car", "card", "care", "careful"), trie.findWords("c"));
        assertEquals(List.of("car", "card", "care", "careful"), trie.findWords("car"));
        assertEquals(List.of("care", "careful"), trie.findWords("care"));
        assertEquals(List.of("card"), trie.findWords("card"));

        assertEquals(List.of("egg"), trie.findWords("e"));
        assertEquals(List.of("egg"), trie.findWords("egg"));

        assertEquals(Collections.emptyList(), trie.findWords("cargo"));
        assertEquals(Collections.emptyList(), trie.findWords(null));
    }

}