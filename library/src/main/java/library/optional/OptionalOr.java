package library.optional;

import java.util.Optional;

public class OptionalOr {

    public String findUser(String userId, String name, String txId) {
        return Optional.ofNullable(userId)
                .flatMap(this::findById)
                .or(() -> findByName(name))
                .or(() -> findByTxId(txId))
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    private Optional<String> findById(String userId) {
        return Optional.empty();
    }

    private Optional<String> findByName(String name) {
        return Optional.empty();
    }

    private Optional<String> findByTxId(String txId) {
        return Optional.empty();
    }

}
