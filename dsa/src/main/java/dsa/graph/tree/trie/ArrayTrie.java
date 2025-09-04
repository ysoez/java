package dsa.graph.tree.trie;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

class ArrayTrie extends AbstractTrie {

    ArrayTrie() {
        super(new ArrayNode(' '));
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
            for (ArrayNode child : children)
                if (child != null)
                    return true;
            return false;
        }

        @Override
        public Node getChild(char ch) {
            return children[index(ch)];
        }

        @Override
        public List<Node> getChildren() {
            // ~ cannot use toList() due to generics
            return Arrays.stream(children).filter(Objects::nonNull).collect(toList());
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
            //
            // ~ a=97, b=98, c=99, d=100
            //
            return ch - 'a';
        }
    }

}
