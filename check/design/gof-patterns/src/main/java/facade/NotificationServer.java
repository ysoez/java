package facade;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
class NotificationServer {

    Connection connect(String ipAddress) {
        return new Connection();
    }

    AuthToken authenticate(String appId, String key) {
        return new AuthToken();
    }

    void send(AuthToken token, Message message, String target) {
        System.out.println("Sending message: " + message.content);
    }

}
