package data_structure.tree.trie;

import lombok.RequiredArgsConstructor;

// allocates array with 26 length (not efficient - waste space)
class ArrayTrie {

    private static final int ALPHABET_SIZE = 26;

    private Node root = new Node(' ');

    void insert(String word) {
        var current = root;
        for (char ch : word.toCharArray()) {
            var index = ch - 'a';
            if (current.children[index] == null)
                current.children[index] = new Node(ch);
            current = current.children[index];
        }
        current.isEndOfWord = true;
    }

    @RequiredArgsConstructor
    private static class Node {
        private final Character value;
        private final Node[] children = new Node[ALPHABET_SIZE];
        private boolean isEndOfWord;

        @Override
        public String toString() {
            return "[" + value + "]";
        }
    }

}
