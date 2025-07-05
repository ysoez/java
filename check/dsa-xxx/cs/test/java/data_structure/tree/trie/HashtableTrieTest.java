package data_structure.tree.trie;

class HashtableTrieTest implements TrieTest {

    @Override
    public Trie emptyTrie() {
        return new HashtableTrie();
    }

}
