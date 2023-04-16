package chain_of_responsibility;

class Logger extends Handler {

    Logger(Handler next) {
        super(next);
    }

    @Override
    boolean doHandle(HttpRequest request) {
        System.out.println("Logging");
        return false;
    }

}
