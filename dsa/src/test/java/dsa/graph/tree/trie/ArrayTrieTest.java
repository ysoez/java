package dsa.graph.tree.trie;

import static org.junit.jupiter.api.Assertions.*;

class ArrayTrieTest extends TrieTest {

    @Override
    Trie newTrie() {
        return new ArrayTrie();
    }

}