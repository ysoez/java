package memento;

import java.util.Deque;
import java.util.LinkedList;

class EditorHistory {

    private final Deque<EditorState> states = new LinkedList<>();

    public void push(EditorState state) {
        states.push(state);
    }

    public EditorState pop() {
        return states.pop();
    }

}
