package dsa.graph.tree.trie;

import dsa.Algorithm;
import dsa.Algorithm.Complexity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static dsa.Algorithm.Complexity.CONSTANT;
import static dsa.Algorithm.Traversal.POST_ORDER;
import static dsa.Algorithm.Traversal.PRE_ORDER;

abstract class AbstractTrie implements Trie {

    private final Node root;

    AbstractTrie(Node root) {
        this.root = root;
    }

    @Override
    @Algorithm(complexity = @Complexity(runtime = "O(L)", space = CONSTANT))
    public void insert(String word) {
        var current = root;
        for (char ch : word.toCharArray()) {
            if (!current.hasChild(ch))
                current.addChild(ch);
            current = current.getChild(ch);
        }
        //
        // ~ mark the last char
        //
        current.setEndOfWord(true);
    }

    @Override
    @Algorithm(complexity = @Complexity(runtime = "O(L)", space = CONSTANT))
    public boolean contains(String word) {
        if (word == null)
            return false;
        var current = root;
        for (char ch : word.toCharArray()) {
            if (!current.hasChild(ch))
                return false;
            current = current.getChild(ch);
        }
        return current.isEndOfWord();
    }

    @Override
    @Algorithm(complexity = @Complexity(runtime = "O(L)", space = CONSTANT), traversal = POST_ORDER)
    public void remove(String word) {
        if (word == null || word.isBlank())
            return;
        //
        // ~ iterate word chars recursively
        //
        remove(root, word, 0);
    }

    private void remove(Node node, String word, int index) {
        //
        // ~ last letter of the word (soft delete)
        //
        if (index == word.length()) {
            node.setEndOfWord(false);
            return;
        }
        var ch = word.charAt(index);
        var child = node.getChild(ch);
        //
        // ~ word not exist
        //
        if (child == null)
            return;
        remove(child, word, index + 1);
        //
        // ~ hard remove a word from the tree if it is not a prefix of any other word
        //
        if (!child.hasChildren() && !child.isEndOfWord())
            node.removeChild(ch);
    }

    @Override
    @Algorithm(complexity = @Complexity(runtime = "O(L)", space = CONSTANT), traversal = PRE_ORDER)
    public List<String> findWords(String prefix) {
        var words = new ArrayList<String>();
        var lastNode = findLastNodeOf(prefix);
        findWords(lastNode, prefix, words);
        return words;
    }

    @Algorithm(complexity = @Complexity(runtime = "O(L)", space = CONSTANT), traversal = PRE_ORDER)
    private void findWords(Node node, String prefix, List<String> words) {
        if (node == null)
            return;
        if (node.isEndOfWord())
            words.add(prefix);
        for (Node child : node.getChildren())
            findWords(child, prefix + child.value(), words);
    }

    @Algorithm(complexity = @Complexity(runtime = "O(L)", space = CONSTANT))
    private Node findLastNodeOf(String prefix) {
        if (prefix == null)
            return null;
        var current = root;
        for (char ch : prefix.toCharArray()) {
            var child = current.getChild(ch);
            //
            // ~ return if prefix is not found
            //
            if (child == null)
                return null;
            current = child;
        }
        return current;
    }

    interface Node {
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
}
