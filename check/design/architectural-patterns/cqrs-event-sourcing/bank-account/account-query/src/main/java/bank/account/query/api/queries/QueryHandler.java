package bank.account.query.api.queries;

import bank.domain.AbstractEntity;

import java.util.List;

public interface QueryHandler {
    List<AbstractEntity> handle(FindAllAccountsQuery query);
    List<AbstractEntity> handle(FindAccountByIdQuery query);
    List<AbstractEntity> handle(FindAccountByHolderQuery query);
    List<AbstractEntity> handle(FindAccountWithBalanceQuery query);
}
