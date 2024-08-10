package data_structure.tree.trie;

import org.junit.jupiter.api.Test;

class ArrayTrieTest {

    @Test
    void insert() {
        var trie = new ArrayTrie();
        trie.insert("cat");
        trie.insert("can");
    }

}