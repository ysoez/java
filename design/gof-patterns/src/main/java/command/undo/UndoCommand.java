package command.undo;

import command.framework.Command;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
class UndoCommand implements Command {

    private final History history;

    @Override
    public void execute() {
        if(!history.isEmpty())
            history.pop().undo();
    }

}
