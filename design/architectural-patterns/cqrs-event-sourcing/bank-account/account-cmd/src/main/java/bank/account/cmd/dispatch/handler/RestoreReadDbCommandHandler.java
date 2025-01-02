package bank.account.cmd.dispatch.handler;

import bank.account.cmd.api.command.*;
import bank.account.cmd.domain.AccountAggregate;
import bank.cqrs.command.CommandHandler;
import bank.es.event.EventSourcingHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

// in fact it is replay event!
@Service
public class RestoreReadDbCommandHandler implements CommandHandler<RestoreReadDbCommand> {

    @Autowired
    private EventSourcingHandler<AccountAggregate> eventSourcingHandler;

    @Override
    public Class<RestoreReadDbCommand> commandType() {
        return RestoreReadDbCommand.class;
    }

    @Override
    public void handle(RestoreReadDbCommand command) {
        eventSourcingHandler.republishEvents();
    }

}
