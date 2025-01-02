package bank.cqrs.query;

import bank.domain.AbstractEntity;

import java.util.List;

public interface QueryDispatcher {

    <T extends AbstractQuery> void registerHandler(Class<T> type, QueryHandler<T> handler);

    <U extends AbstractEntity> List<U> send(AbstractQuery query);

}
