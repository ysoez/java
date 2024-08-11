package grpc.echo;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@Slf4j
class EchoServerRunner {

    public static void main(String[] args) throws IOException, InterruptedException {
        var server = new EchoServer();
        server.start();
        server.blockUntilShutdown();
    }

}