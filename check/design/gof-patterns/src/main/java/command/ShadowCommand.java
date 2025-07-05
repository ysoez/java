package command;

import command.framework.Command;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
class ShadowCommand implements Command {

    @Override
    public void execute() {
        System.out.println("Shadow background");
    }

}
