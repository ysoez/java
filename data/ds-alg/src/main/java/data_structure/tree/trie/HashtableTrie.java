package data_structure.tree.trie;

import lombok.RequiredArgsConstructor;
import util.Algorithm;
import util.Algorithm.Complexity;
import util.Traversal;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static util.Algorithm.Complexity.Value.CONSTANT;
import static util.Algorithm.Implementation.RECURSIVE;
import static util.Traversal.Type.POST_ORDER;
import static util.Traversal.Type.PRE_ORDER;

class HashtableTrie implements Trie {

    private final Node root = new Node(' ');

    @Override
    @Algorithm(complexity = @Complexity(runtime = CONSTANT, space = CONSTANT))
    public void insert(String word) {
        var current = root;
        for (char ch : word.toCharArray()) {
            if (!current.hasChild(ch))
                current.addChild(ch);
            current = current.getChild(ch);
        }
        current.isEndOfWord = true;
    }

    @Override
    @Algorithm(complexity = @Complexity(runtime = CONSTANT, space = CONSTANT))
    public boolean contains(String word) {
        if (word == null)
            return false;
        var current = root;
        for (char ch : word.toCharArray()) {
            if (!current.hasChild(ch))
                return false;
            current = current.getChild(ch);
        }
        return current.isEndOfWord;
    }

    @Override
    @Traversal(POST_ORDER)
    @Algorithm(complexity = @Complexity(runtime = CONSTANT, space = CONSTANT), implementation = RECURSIVE)
    public void remove(String word) {
        if (word == null)
            return;
        remove(word, root, 0);
    }

    private void remove(String word, Node node, int index) {
        if (index == word.length()) {
            node.isEndOfWord = false;
            return;
        }
        var ch = word.charAt(index);
        Node child = node.getChild(ch);
        if (child == null)
            return;
        remove(word, child, index + 1);
        if (!child.hasChildren() && !child.isEndOfWord)
            node.removeChild(ch);
    }

    @Override
    @Traversal(PRE_ORDER)
    @Algorithm(complexity = @Complexity(runtime = CONSTANT, space = CONSTANT), implementation = RECURSIVE)
    public List<String> findWords(String prefix) {
        Node lastNode = findLastNodeOf(prefix);
        List<String> words = new ArrayList<>();
        findWords(lastNode, prefix, words);
        return words;
    }

    private Node findLastNodeOf(String prefix) {
        if (prefix == null)
            return null;
        var current = root;
        for (char ch : prefix.toCharArray()) {
            Node child = current.getChild(ch);
            if (child == null)
                return null;
            current = child;
        }
        return current;
    }

    private void findWords(Node node, String prefix, List<String> words) {
        if (node == null)
            return;
        if (node.isEndOfWord)
            words.add(prefix);
        for (Node child : node.getChildren())
            findWords(child, prefix + child.value, words);
    }


    void traverse(Traversal.Type type) {
        switch (type) {
            case PRE_ORDER -> preOrderTraverse(root);
            case POST_ORDER -> postOrderTraverse(root);
        }
    }

    private void preOrderTraverse(Node node) {
        System.out.println(node.value);
        for (Node child : node.getChildren())
            preOrderTraverse(child);
    }

    private void postOrderTraverse(Node node) {
        for (Node child : node.getChildren())
            postOrderTraverse(child);
        System.out.println(node.value);
    }

    @RequiredArgsConstructor
    private static class Node {
        private final Character value;
        private final HashMap<Character, Node> children = new HashMap<>();

        private boolean isEndOfWord;

        boolean hasChild(char ch) {
            return children.containsKey(ch);
        }

        boolean hasChildren() {
            return !children.isEmpty();
        }

        void addChild(char ch) {
            children.put(ch, new Node(ch));
        }

        Node getChild(char ch) {
            return children.get(ch);
        }

        Node[] getChildren() {
            return children.values().toArray(new Node[0]);
        }

        void removeChild(char ch) {
            children.remove(ch);
        }

        @Override
        public String toString() {
            return String.valueOf(value);
        }
    }

}
