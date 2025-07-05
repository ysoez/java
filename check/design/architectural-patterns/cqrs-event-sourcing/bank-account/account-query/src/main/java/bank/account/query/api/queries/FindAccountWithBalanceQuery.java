package bank.account.query.api.queries;

import bank.account.query.api.dto.EqualityType;
import bank.cqrs.query.AbstractQuery;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FindAccountWithBalanceQuery extends AbstractQuery {
    private EqualityType equalityType;
    private double balance;
}
