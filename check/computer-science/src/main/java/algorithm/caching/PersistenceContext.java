package algorithm.caching;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.Map;

class PersistenceContext {
    final Map<String, Object> cache;
    final Deque<Action> actionQueue;
    final Database database;

    public PersistenceContext() {
        this.cache = new HashMap<>();
        this.actionQueue = new ArrayDeque<>(100);
        database = new Database() {
            @Override
            public void insert(String key, Object entity) {
                System.out.println("Persisting entity with key: " + key);
            }

            @Override
            public Object selectById(String id) {
                System.out.println("Retrieving entity with key: " + id);
                return null;
            }

            @Override
            public void delete(String key) {
                System.out.println("Removing entity with key: " + key);
            }
        };
    }

    public void persist(String key, Object entity) {
        cache.put(key, entity);
        Action action = new Action(key, ActionType.PERSIST);
        actionQueue.offer(action);
    }

    public void remove(String key) {
        cache.remove(key);
        Action action = new Action(key, ActionType.REMOVE);
        actionQueue.offer(action);
    }

    public Object find(String key) {
        Object entity = cache.get(key);
        if (entity == null) {
            // retrieve the entity from the underlying data source if not found in the cache
            entity = retrieveFromDataSource(key);
            if (entity != null) {
                cache.put(key, entity);
            }
        }
        return entity;
    }

    public void flush() {
        while (!actionQueue.isEmpty()) {
            Action action = actionQueue.poll();
            if (action != null) {
                processAction(action);
            }
        }
    }

    private void processAction(Action action) {
        switch (action.type) {
            case PERSIST -> persistToDataSource(action.key, cache.get(action.key));
            case REMOVE -> removeFromDataSource(action.key);
        }
    }

    private void persistToDataSource(String key, Object entity) {
        database.insert(key, entity);
    }

    private void removeFromDataSource(String key) {
        database.delete(key);
    }

    private Object retrieveFromDataSource(String key) {
        return database.selectById(key);
    }

    private enum ActionType {
        PERSIST,
        REMOVE
    }

    private record Action(String key, ActionType type) {}

    private interface Database {

        void insert(String key, Object entity);

        Object selectById(String id);

        void delete(String key);

    }

}

