package persistence.util.transaction;

public interface TransactionLifecycle {

    default void beforeTransaction() {
    }

    default void afterTransaction() {
    }

}
