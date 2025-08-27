package dsa.graph.tree.trie;

import java.util.*;

class Trie {

    private final Node root = new HashMapNode(' ');

    void insert(String word) {
        var current = root;
        for (char ch : word.toCharArray()) {
            if (!current.hasChild(ch)) {
                current.addChild(ch);
            }
            current = current.getChild(ch);
        }
        // current is the last char
        current.setEndOfWord(true);
    }

    boolean contains(String word) {
        if (word == null)
            return false;
        var current = root;
        for (char ch : word.toCharArray()) {
            if (!current.hasChild(ch))
                return false;
            current = current.getChild(ch);
        }
        // last node (char) of this word
        return current.isEndOfWord();
    }

    // post order
    void remove(String word) {
        if (word == null)
            return;
        remove(root, word, 0);
    }

    private void remove(Node node, String word, int index) {
        if (index == word.length()) {
            // write flow on a piece of paper to verify
            node.setEndOfWord(false);
            return;
        }
        var ch = word.charAt(index);
        var child = node.getChild(ch);
        if (child == null)
            return;
        remove(child, word, index + 1);
        if (!child.hasChildren() && !child.isEndOfWord()) {
            node.removeChild(ch);
        }

    }

    // pre-order
    public List<String> findWords(String prefix) {
        List<String> words = new ArrayList<>();
        var lastNode = findLastNodeOf(prefix);
        findWords(lastNode, prefix, words);
        return words;
    }

    private void findWords(Node node, String prefix, List<String> words) {
        if (node == null)
            return;
        if (node.isEndOfWord())
            words.add(prefix);
        for (var child : node.getChildren())
            findWords(child, prefix + child.value(), words);
    }

    private Node findLastNodeOf(String prefix) {
        if (prefix == null)
            return null;
        var current = root;
        for (char ch : prefix.toCharArray()) {
            var child = current.getChild(ch);
            if (child == null)
                return null;
            current = child;
        }
        return current;
    }

    private interface Node {
        char value();
        boolean isEndOfWord();
        boolean hasChild(char ch);
        boolean hasChildren();
        Node getChild(char ch);
        Collection<Node> getChildren();
        void addChild(char ch);
        void removeChild(char ch);
        void setEndOfWord(boolean val);
    }

    private static class ArrayNode implements Node {
        private static final int ALPHABET_SIZE = 26;
        char value;
        ArrayNode[] children = new ArrayNode[ALPHABET_SIZE];
        boolean isEndOfWord;

        ArrayNode(char value) {
            this.value = value;
        }

        @Override
        public char value() {
            return value;
        }

        @Override
        public boolean isEndOfWord() {
            return isEndOfWord;
        }

        @Override
        public boolean hasChild(char ch) {
            return children[index(ch)] != null;
        }

        @Override
        public boolean hasChildren() {
            throw new UnsupportedOperationException();
        }

        @Override
        public Node getChild(char ch) {
            return children[index(ch)];
        }

        @Override
        public List<Node> getChildren() {
            return List.of();
        }

        @Override
        public void addChild(char ch) {
            children[index(ch)] = new ArrayNode(ch);
        }

        @Override
        public void removeChild(char ch) {
            children[index(ch)] = null;
        }

        @Override
        public void setEndOfWord(boolean val) {
            isEndOfWord = val;
        }

        @Override
        public String toString() {
            return String.valueOf(value);
        }

        private int index(char ch) {
            // ~ a=97, b=98, c=99, d=100
            return ch - 'a';
        }
    }

    private static class HashMapNode implements Node {
        private final char value;
        private final Map<Character, Node> children = new HashMap<>();
        private boolean isEndOfWord;

        HashMapNode(char value) {
            this.value = value;
        }

        @Override
        public char value() {
            return value;
        }

        @Override
        public boolean isEndOfWord() {
            return isEndOfWord;
        }

        @Override
        public boolean hasChild(char ch) {
            return children.containsKey(ch);
        }

        @Override
        public boolean hasChildren() {
            return !children.isEmpty();
        }

        @Override
        public Node getChild(char ch) {
            return children.get(ch);
        }

        @Override
        public Collection<Node> getChildren() {
            return children.values();
        }

        @Override
        public void addChild(char ch) {
            children.put(ch, new HashMapNode(ch));
        }

        @Override
        public void removeChild(char ch) {
            children.remove(ch);
        }

        @Override
        public void setEndOfWord(boolean val) {
            isEndOfWord = val;
        }

        @Override
        public String toString() {
            return String.valueOf(value);
        }
    }

}
