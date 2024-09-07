package exception;

class ExceptionSneakyThrow {

    public static void main(String[] args) {
        if (args.length != 1) {
            sneakyThrow(new Exception("single argument expected"));
        }
    }

    @SuppressWarnings("unchecked")
    private static <E extends Throwable> void sneakyThrow(Throwable e) throws E {
        throw (E) e;
    }

}
