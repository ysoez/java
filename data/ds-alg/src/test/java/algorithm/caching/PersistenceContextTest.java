package algorithm.caching;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class PersistenceContextTest {

    private PersistenceContext persistenceContext;

    @BeforeEach
    void setUp() {
        persistenceContext = new PersistenceContext();
    }

    @Test
    void testPersistAndFind() {
        String key = "key";
        String entity = "value";

        persistenceContext.persist(key, entity);
        Object foundEntity = persistenceContext.find(key);

        assertEquals(entity, foundEntity);
    }

    @Test
    void testRemoveAndFind() {
        String key = "key";
        String entity = "value";

        persistenceContext.persist(key, entity);
        persistenceContext.remove(key);
        Object foundEntity = persistenceContext.find(key);

        assertNull(foundEntity);
    }

    @Test
    void testFlush() {
        String key1 = "key1";
        String entity1 = "value1";
        String key2 = "key2";
        String entity2 = "value2";

        persistenceContext.persist(key1, entity1);
        persistenceContext.persist(key2, entity2);

        // verify that the actions are in the queue
        assertEquals(2, persistenceContext.actionQueue.size());

        persistenceContext.flush();

        // verify that the actions are processed and the queue is empty
        assertEquals(0, persistenceContext.actionQueue.size());

        Object foundEntity1 = persistenceContext.find(key1);
        Object foundEntity2 = persistenceContext.find(key2);

        assertEquals(entity1, foundEntity1);
        assertEquals(entity2, foundEntity2);
    }
}
