package bank.account.cmd.api.command;

import bank.cqrs.command.AbstractCommand;

public class CloseAccountCommand extends AbstractCommand {

    public CloseAccountCommand(String id) {
        super(id);
    }

}
