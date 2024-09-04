package persistence.util.transaction;

import jakarta.persistence.EntityManager;

import java.util.function.Consumer;

@FunctionalInterface
public interface JPATransactionVoidCallback extends TransactionLifecycle, Consumer<EntityManager> {

}
