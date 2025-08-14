package library.optional;

import java.util.Optional;

class OptionalOr {

    public static void main(String[] args) {
        System.out.println(new UserRepo().by("1", "admin", 42));
    }

    private static class UserRepo {
        String by(String userId, String name, int age) {
            return Optional.ofNullable(userId)
                    .flatMap(this::byUserId)
                    .or(() -> byName(name))
                    .or(() -> byAge(age))
                    .orElseThrow(() -> new RuntimeException("user not found: " + userId));
        }
        Optional<String> byUserId(String userId) {
            return Optional.empty();
        }
        Optional<String> byName(String name) {
            return Optional.empty();
        }
        Optional<String> byAge(int age) {
            return Optional.of("sudo");
        }
    }

}
