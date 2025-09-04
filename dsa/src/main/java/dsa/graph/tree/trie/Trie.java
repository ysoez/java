package dsa.graph.tree.trie;

import java.util.List;

interface Trie {

    void insert(String word);

    boolean contains(String word);

    void remove(String word);

    List<String> findWords(String prefix);

}
