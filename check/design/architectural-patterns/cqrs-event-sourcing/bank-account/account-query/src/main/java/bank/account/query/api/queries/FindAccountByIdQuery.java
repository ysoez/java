package bank.account.query.api.queries;

import bank.cqrs.query.AbstractQuery;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FindAccountByIdQuery extends AbstractQuery {
    private String id;
}
