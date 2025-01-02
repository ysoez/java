package bank.account.cmd.dispatch.handler;

import bank.account.cmd.api.command.WithdrawFundsCommand;
import bank.account.cmd.domain.AccountAggregate;
import bank.cqrs.command.CommandHandler;
import bank.es.event.EventSourcingHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WithdrawFundsCommandHandler implements CommandHandler<WithdrawFundsCommand> {

    private final EventSourcingHandler<AccountAggregate> eventSourcingHandler;

    @Override
    public Class<WithdrawFundsCommand> commandType() {
        return WithdrawFundsCommand.class;
    }

    @Override
    public void handle(WithdrawFundsCommand command) {
        var aggregate = eventSourcingHandler.getById(command.getId());
        if (command.getAmount() > aggregate.getBalance()) {
            throw new IllegalStateException("Withdrawal declined, insufficient funds!");
        }
        aggregate.withdrawFunds(command.getAmount());
        eventSourcingHandler.save(aggregate);
    }

}
