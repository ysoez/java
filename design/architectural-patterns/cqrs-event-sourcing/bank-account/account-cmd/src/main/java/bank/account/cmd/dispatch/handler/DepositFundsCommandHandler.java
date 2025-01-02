package bank.account.cmd.dispatch.handler;

import bank.account.cmd.api.command.DepositFundsCommand;
import bank.account.cmd.domain.AccountAggregate;
import bank.cqrs.command.CommandHandler;
import bank.es.event.EventSourcingHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DepositFundsCommandHandler implements CommandHandler<DepositFundsCommand> {

    private final EventSourcingHandler<AccountAggregate> eventSourcingHandler;

    @Override
    public Class<DepositFundsCommand> commandType() {
        return DepositFundsCommand.class;
    }

    @Override
    public void handle(DepositFundsCommand command) {
        var aggregate = eventSourcingHandler.getById(command.getId());
        aggregate.depositFunds(command.getAmount());
        eventSourcingHandler.save(aggregate);
    }

}
