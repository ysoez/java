package chain_of_responsibility;

class Authenticator extends Handler {

    Authenticator(Handler next) {
        super(next);
    }

    @Override
    boolean doHandle(HttpRequest request) {
        System.out.println("Authentication");
        boolean isAuthenticated = "admin".equals(request.username()) && "123".equals(request.password());
        return !isAuthenticated;
    }
}
