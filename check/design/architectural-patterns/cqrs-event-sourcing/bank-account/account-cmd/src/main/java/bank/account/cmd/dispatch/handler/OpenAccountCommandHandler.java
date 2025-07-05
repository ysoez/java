package bank.account.cmd.dispatch.handler;

import bank.account.cmd.api.command.OpenAccountCommand;
import bank.account.cmd.domain.AccountAggregate;
import bank.cqrs.command.CommandHandler;
import bank.es.event.EventSourcingHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OpenAccountCommandHandler implements CommandHandler<OpenAccountCommand> {

    private final EventSourcingHandler<AccountAggregate> eventSourcingHandler;

    @Override
    public Class<OpenAccountCommand> commandType() {
        return OpenAccountCommand.class;
    }

    @Override
    public void handle(OpenAccountCommand command) {
        var aggregate = new AccountAggregate(command);
        eventSourcingHandler.save(aggregate);
    }

}
