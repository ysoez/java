package bank.es.event;

import bank.es.AggregateRoot;

public interface EventSourcingHandler<T> {

    void save(AggregateRoot aggregate);

    T getById(String id);

    void republishEvents();

}
