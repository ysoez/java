package command.undo;

import command.framework.Command;

interface UndoableCommand extends Command {
    void undo();
}
