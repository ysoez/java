package command.undo;

import java.util.ArrayDeque;
import java.util.Deque;

class History {

    private final Deque<UndoableCommand> commands = new ArrayDeque<>();

    void push(UndoableCommand command) {
        commands.add(command);
    }

    UndoableCommand pop() {
        return commands.pop();
    }

    boolean isEmpty() {
        return commands.isEmpty();
    }

}
