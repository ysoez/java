package bank.cqrs.query;

import bank.domain.AbstractEntity;

import java.util.List;

@FunctionalInterface
public interface QueryHandler<T extends AbstractQuery> {

    List<AbstractEntity> handle(T query);

}
