package memento;

import java.util.ArrayDeque;
import java.util.Deque;

class DocumentHistory {

    private final Deque<DocumentState> states = new ArrayDeque<>();

    public void push(DocumentState state) {
        states.push(state);
    }

    public DocumentState pop() {
        return states.pop();
    }

}
