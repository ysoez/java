package dsa.graph.tree.trie;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TrieTest {

    private final Trie trie = new Trie();

    @Test
    void lokkup() {
        trie.insert("cat");
        trie.insert("can");
    }

    @Test
    void lokkupXX() {
        trie.insert("canada");
        assertFalse(trie.contains("can"));
    }

    @Test
    void remove() {
        trie.insert("car");
        trie.insert("care");

        trie.remove("car");

        assertFalse(trie.contains("car"));
        assertTrue(trie.contains("care"));
    }

    @Test
    void remove2() {
        trie.insert("car");
        trie.insert("care");

        trie.remove("care");

        assertTrue(trie.contains("car"));
        assertFalse(trie.contains("care"));
    }

    @Test
    void remove3() {
        trie.insert("car");
        trie.insert("care");

        trie.remove("book");
        trie.remove("");
        trie.remove(null);

        assertTrue(trie.contains("car"));
        assertTrue(trie.contains("care"));
    }

    @Nested
    class FindWord {
        @Test
        void words() {
            trie.insert("car");
            trie.insert("card");
            trie.insert("care");
            trie.insert("careful");
            trie.insert("egg");

            assertEquals(Collections.emptyList(), trie.findWords(""));
            assertEquals(List.of("car", "card", "care", "careful"), trie.findWords("c"));
            assertEquals(List.of("car", "card", "care", "careful"), trie.findWords("car"));
            assertEquals(List.of("care", "careful"), trie.findWords("care"));
            assertEquals(List.of("card"), trie.findWords("card"));

            assertEquals(List.of("egg"), trie.findWords("e"));
            assertEquals(List.of("egg"), trie.findWords("egg"));

            assertEquals(Collections.emptyList(), trie.findWords("cargo"));
//            assertEquals(Collections.emptyList(), trie.findWords(null));
        }
    }

}