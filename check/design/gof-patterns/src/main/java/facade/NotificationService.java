package facade;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
class NotificationService {

    void send(String message, String targetDevice) {
        var server = new NotificationServer();
        var connection = server.connect("127.0.0.1");
        var token = server.authenticate("appId", "key");
        server.send(token, new Message(message), targetDevice);
        connection.disconnect();
    }

}
