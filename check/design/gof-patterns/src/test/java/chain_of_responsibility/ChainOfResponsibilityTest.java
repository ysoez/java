package chain_of_responsibility;

import org.junit.jupiter.api.Test;

class ChainOfResponsibilityTest {

    @Test
    void processRequest() {
        var encryptor = new Encryptor(null);
        var logger = new Logger(encryptor);
        var authenticator = new Authenticator(logger);
        var webServer = new WebServer(authenticator);

        webServer.handle(new HttpRequest("admin", "123"));
    }

}