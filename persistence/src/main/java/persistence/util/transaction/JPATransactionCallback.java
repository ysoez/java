package persistence.util.transaction;

import jakarta.persistence.EntityManager;

import java.util.function.Function;

@FunctionalInterface
public interface JPATransactionCallback<T> extends TransactionLifecycle, Function<EntityManager, T> {

}
