package library.concurrency.xxx;

class ThreadExceptionHandler {

    public static void main(String[] args) {
        var thread = new Thread(() -> {
            throw new RuntimeException("Error happened");
        });
        thread.setUncaughtExceptionHandler((aThread, error) -> {
            System.out.printf("Handling error: thread=[%s], errorMessage=[%s]", aThread.getName(), error.getMessage());
        });
        thread.start();
    }

}
