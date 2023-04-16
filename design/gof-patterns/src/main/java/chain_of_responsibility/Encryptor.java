package chain_of_responsibility;

class Encryptor extends Handler {

    Encryptor(Handler next) {
        super(next);
    }

    @Override
    boolean doHandle(HttpRequest request) {
        System.out.println("Encryption");
        return false;
    }

}
