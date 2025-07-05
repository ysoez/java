package data_structure.tree.trie;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

interface TrieTest {

    Trie emptyTrie();

    default Trie createTrie(String... words) {
        Trie trie = emptyTrie();
        for (String word : words)
            trie.insert(word);
        return trie;
    }

    @Test
    default void insertAndLookup() {
        var trie = emptyTrie();
        assertFalse(trie.contains(""));
        assertFalse(trie.contains("abc"));

        trie = createTrie("canada");

        assertFalse(trie.contains("can"));
        assertFalse(trie.contains(""));
        assertFalse(trie.contains(null));

        assertTrue(trie.contains("canada"));
    }

    @Test
    default void removeEmptyStringOrNull() {
        var trie = createTrie("car", "care");

        trie.remove("");
        assertTrue(trie.contains("car"));
        assertTrue(trie.contains("care"));

        trie.remove(null);
        assertTrue(trie.contains("car"));
        assertTrue(trie.contains("care"));
    }

    @Test
    default void removeNotExistedWord() {
        var trie = createTrie("car", "care", "book");

        trie.remove("book");

        assertTrue(trie.contains("car"));
        assertTrue(trie.contains("care"));
        assertFalse(trie.contains("book"));
    }

    @Test
    default void removeShorterWord() {
        var trie = createTrie("car", "care");

        trie.remove("car");

        assertFalse(trie.contains("car"));
        assertTrue(trie.contains("care"));
    }

    @Test
    default void removeLongerWord() {
        var trie = createTrie("car", "care");

        trie.remove("care");

        assertTrue(trie.contains("car"));
        assertFalse(trie.contains("care"));
    }

    @Test
    default void findWords() {
        var trie = createTrie("car", "card", "care", "careful", "egg");

        assertEquals(List.of(), trie.findWords(null));
        assertEquals(List.of("car", "card", "care", "careful", "egg"), trie.findWords(""));

        assertEquals(List.of("car", "card", "care", "careful"), trie.findWords("c"));
        assertEquals(List.of("car", "card", "care", "careful"), trie.findWords("car"));
        assertEquals(List.of("card"), trie.findWords("card"));
        assertEquals(List.of("care", "careful"), trie.findWords("care"));

        assertEquals(List.of("egg"), trie.findWords("egg"));
        assertEquals(List.of("egg"), trie.findWords("e"));

        assertEquals(List.of(), trie.findWords("cargo"));
    }

}
