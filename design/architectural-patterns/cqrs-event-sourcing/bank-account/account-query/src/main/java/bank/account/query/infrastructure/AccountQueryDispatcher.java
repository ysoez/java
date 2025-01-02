package bank.account.query.infrastructure;

import bank.domain.AbstractEntity;
import bank.cqrs.query.AbstractQuery;
import bank.cqrs.query.QueryDispatcher;
import bank.cqrs.query.QueryHandler;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Service
public class AccountQueryDispatcher implements QueryDispatcher {

    private final Map<Class<? extends AbstractQuery>, List<QueryHandler>> routes = new HashMap<>();

    @Override
    public <T extends AbstractQuery> void registerHandler(Class<T> type, QueryHandler<T> handler) {
        var handlers = routes.computeIfAbsent(type, c -> new LinkedList<>());
        handlers.add(handler);
    }

    @Override
    public <U extends AbstractEntity> List<U> send(AbstractQuery query) {
        var handlers = routes.get(query.getClass());
        if (handlers == null || handlers.size() <= 0) {
            throw new RuntimeException("No command handler was registered!");
        }
        if (handlers.size() > 1) {
            throw new RuntimeException("Cannot send command to more than one handler!");
        }
        return handlers.get(0).handle(query);
    }
}
