package netty.transport;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

@Slf4j
class OldIoServer {

    public void serve(int port) {
        try (final var socket = new ServerSocket(port)) {
            while (true) {
                final Socket clientSocket = socket.accept();
                log.debug("Accepted connection from: {}", clientSocket);
                System.out.println();
                new Thread(() -> {
                    OutputStream out;
                    try (clientSocket) {
                        out = clientSocket.getOutputStream();
                        out.write("Hi!\r\n".getBytes(StandardCharsets.UTF_8));
                        out.flush();
                    } catch (IOException e) {
                        log.error("Error happened: ", e);
                    }
                }).start();
            }
        } catch (IOException e) {
            log.error("Error happened: ", e);
        }
    }
}