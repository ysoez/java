package bank.es.event;

import java.util.List;

public interface EventStore {

    void saveEvents(String aggregateId, Iterable<AbstractEvent> events, int expectedVersion);

    List<AbstractEvent> getEvents(String aggregateId);

    List<String> getAggregateIds();

}
