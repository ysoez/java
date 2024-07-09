package reflection.annotation.repeatable;

import java.lang.annotation.*;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

class Authorization {

    public static void main(String[] args) throws Throwable {
        var admin = new User(Role.ADMIN);
        var support = new User(Role.SUPPORT);
        var anon = new User(Role.ANONYMOUS);
        var api = new AccountApi();

        api.createAccount(admin, "test@mail.com");
        api.readAccount(admin, "test@mail.com");
        api.updateAccount(admin, "updated-test@mail.com");

        api.readAccount(support, "test@mail.com");
        api.createAccount(support, "test@mail.com");
        api.updateAccount(support, "updated-test@mail.com");

        api.readAccount(anon, "test@mail.com");
    }

    @Permissions(role = Role.SUPPORT, allowed = OperationType.READ)
    @Permissions(role = Role.ADMIN, allowed = {OperationType.READ, OperationType.WRITE})
    private static class AccountApi {

        @MethodOperations(OperationType.WRITE)
        public void createAccount(User user, String email) {
            PermissionsChecker.checkPermissions(user, this, "createAccount");
            System.out.println("Created account: " + email);
        }

        @MethodOperations(OperationType.READ)
        public void readAccount(User user, String email) throws Throwable {
            PermissionsChecker.checkPermissions(user, this, "readAccount");
            System.out.println("Reading account: " + email);
        }

        @MethodOperations({OperationType.READ, OperationType.WRITE})
        public void updateAccount(User user, String email) {
            PermissionsChecker.checkPermissions(user, this, "updateAccount");
            System.out.println("Updated account: " + email);
        }

    }

    private static class PermissionsChecker {

        static void checkPermissions(User user, Object resource, String invokedMethodName)  {
            Permissions[] allPermissions = getClassAnnotatedPermissions(resource);
            OperationType[] methodOperationTypes = getMethodOperationTypes(resource, invokedMethodName);
            List<OperationType> userAllowedOperations = findUserAllowedOperations(allPermissions, user);
            for (OperationType methodOperationsTypes : methodOperationTypes) {
                if (!userAllowedOperations.contains(methodOperationsTypes)) {
                    throw new SecurityException(String.format("Unauthorized: userAllowedOperations=%s, methodOperationsTypes=%s",
                            userAllowedOperations, methodOperationsTypes));
                }
            }
            System.out.println("User authorized");
        }

        private static OperationType[] getMethodOperationTypes(Object resource, String invokedMethodName) {
            Method invokedMethod = getInvokedMethod(resource, invokedMethodName);
            MethodOperations methodOperations = getInvokedMethodOperations(invokedMethod);
            return methodOperations.value();
        }

        private static Permissions[] getClassAnnotatedPermissions(Object resource) {
            return resource.getClass().getAnnotationsByType(Permissions.class);
        }

        private static List<OperationType> findUserAllowedOperations(Permissions[] allPermissions, User user) {
            for (Permissions currentPermissions : allPermissions) {
                if (user.role.equals(currentPermissions.role())) {
                    return Arrays.asList(currentPermissions.allowed());
                }
            }
            return Collections.emptyList();
        }

        private static MethodOperations getInvokedMethodOperations(Method callerMethod) {
            return callerMethod.getAnnotation(MethodOperations.class);
        }


        private static Method getInvokedMethod(Object callerObject, String methodName) {
            return Arrays.stream(callerObject.getClass().getDeclaredMethods())
                    .filter(method -> method.getName().equals(methodName))
                    .findFirst()
                    .orElseThrow(() -> new IllegalStateException(String.format("The passed method name :%s does not exist", methodName)));
        }
    }

    @Target(ElementType.TYPE)
    @Repeatable(PermissionsContainer.class)
    @interface Permissions {
        Role role();

        OperationType[] allowed();
    }

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.TYPE)
    @interface PermissionsContainer {
        Permissions[] value();
    }

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.METHOD)
    @interface MethodOperations {
        OperationType[] value();
    }

    enum Role {
        ADMIN, SUPPORT, ANONYMOUS
    }

    enum OperationType {
        READ, WRITE
    }

    private static class User {
        private final Role role;

        User(Role role) {
            this.role = role;
        }
    }

}
