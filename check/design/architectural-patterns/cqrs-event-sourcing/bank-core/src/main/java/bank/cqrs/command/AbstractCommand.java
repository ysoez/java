package bank.cqrs.command;

import bank.Message;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public abstract class AbstractCommand extends Message {

    public AbstractCommand(String id) {
        super(id);
    }

}
