package command;

import command.framework.Command;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
class ResizeCommand implements Command {

    @Override
    public void execute() {
        System.out.println("Resized");
    }

}
