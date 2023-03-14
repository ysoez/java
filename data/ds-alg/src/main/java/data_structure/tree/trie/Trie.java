package data_structure.tree.trie;

import java.util.List;

interface Trie {

    void insert(String word);

    boolean contains(String word);

    List<String> findWords(String prefix);

    void remove(String word);

}
