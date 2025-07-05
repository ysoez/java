package bank.account.cmd.dispatch.handler;

import bank.account.cmd.api.command.CloseAccountCommand;
import bank.account.cmd.domain.AccountAggregate;
import bank.cqrs.command.CommandHandler;
import bank.es.event.EventSourcingHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CloseAccountCommandHandler implements CommandHandler<CloseAccountCommand> {

    private final EventSourcingHandler<AccountAggregate> eventSourcingHandler;

    @Override
    public Class<CloseAccountCommand> commandType() {
        return CloseAccountCommand.class;
    }

    @Override
    public void handle(CloseAccountCommand command) {
        var aggregate = eventSourcingHandler.getById(command.getId());
        aggregate.closeAccount();
        eventSourcingHandler.save(aggregate);
    }

}
