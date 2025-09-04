package dsa.graph.tree.trie;

import java.util.*;

class HashMapTrie extends AbstractTrie {

    HashMapTrie() {
        super(new HashMapNode(' '));
    }

//
//    // post order
//    void remove(String word) {
//        if (word == null)
//            return;
//        remove(root, word, 0);
//    }
//

//
//    // pre-order
//    public List<String> findWords(String prefix) {
//        List<String> words = new ArrayList<>();
//        var lastNode = findLastNodeOf(prefix);
//        findWords(lastNode, prefix, words);
//        return words;
//    }
//
//    private void findWords(Node node, String prefix, List<String> words) {
//        if (node == null)
//            return;
//        if (node.isEndOfWord())
//            words.add(prefix);
//        for (var child : node.getChildren())
//            findWords(child, prefix + child.value(), words);
//    }
//

//
//    private static class ArrayNode implements Node {
//        private static final int ALPHABET_SIZE = 26;
//        char value;
//        ArrayNode[] children = new ArrayNode[ALPHABET_SIZE];
//        boolean isEndOfWord;
//
//        ArrayNode(char value) {
//            this.value = value;
//        }
//
//        @Override
//        public char value() {
//            return value;
//        }
//
//        @Override
//        public boolean isEndOfWord() {
//            return isEndOfWord;
//        }
//
//        @Override
//        public boolean hasChild(char ch) {
//            return children[index(ch)] != null;
//        }
//
//        @Override
//        public boolean hasChildren() {
//            throw new UnsupportedOperationException();
//        }
//
//        @Override
//        public Node getChild(char ch) {
//            return children[index(ch)];
//        }
//
//        @Override
//        public List<Node> getChildren() {
//            return List.of();
//        }
//
//        @Override
//        public void addChild(char ch) {
//            children[index(ch)] = new ArrayNode(ch);
//        }
//
//        @Override
//        public void removeChild(char ch) {
//            children[index(ch)] = null;
//        }
//
//        @Override
//        public void setEndOfWord(boolean val) {
//            isEndOfWord = val;
//        }
//
//        @Override
//        public String toString() {
//            return String.valueOf(value);
//        }
//
//        private int index(char ch) {
//            // ~ a=97, b=98, c=99, d=100
//            return ch - 'a';
//        }
//    }
//
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
