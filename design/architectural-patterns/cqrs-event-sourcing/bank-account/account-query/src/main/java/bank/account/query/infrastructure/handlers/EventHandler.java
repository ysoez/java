package bank.account.query.infrastructure.handlers;

import bank.domain.event.AccountClosedEvent;
import bank.domain.event.AccountOpenedEvent;
import bank.domain.event.FundsDepositedEvent;
import bank.domain.event.FundsWithdrawnEvent;

public interface EventHandler {

    void on(AccountOpenedEvent event);

    void on(FundsDepositedEvent event);

    void on(FundsWithdrawnEvent event);

    void on(AccountClosedEvent event);

}
