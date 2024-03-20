package grpc.serialization;

import grpc.Utils;
import grpc.echo.EchoReply;
import grpc.echo.EchoRequest;
import grpc.echo.EchoServiceGrpc;
import io.grpc.*;
import io.grpc.stub.ServerCalls.UnaryMethod;
import io.grpc.stub.StreamObserver;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

import static io.grpc.stub.ServerCalls.asyncUnaryCall;

@Slf4j
class JsonServer {

    private Server server;

    public static void main(String[] args) throws IOException, InterruptedException {
        var server = new JsonServer();
        server.start();
        server.blockUntilShutdown();
    }

    private void start() throws IOException {
        int port = 50051;
        server = Grpc.newServerBuilderForPort(port, InsecureServerCredentials.create())
                .addService(new EchoImpl())
                .build()
                .start();
        log.info("Server started, listening on: {}", port);
        Utils.addShutdownHook(server);
    }

    private void blockUntilShutdown() throws InterruptedException {
        if (server != null) {
            server.awaitTermination();
        }
    }

    private static class EchoImpl implements BindableService {

        private void echo(EchoRequest req, StreamObserver<EchoReply> responseObserver) {
            EchoReply reply = EchoReply.newBuilder().setMessage("hello from server").build();
            responseObserver.onNext(reply);
            responseObserver.onCompleted();
        }

        @Override
        public ServerServiceDefinition bindService() {
            return io.grpc.ServerServiceDefinition.builder(EchoServiceGrpc.getServiceDescriptor().getName())
                    .addMethod(JsonClient.EchoJsonStub.ECHO_METHOD, asyncUnaryCall(new UnaryMethod<EchoRequest, EchoReply>() {
                        @Override
                        public void invoke(EchoRequest request, StreamObserver<EchoReply> responseObserver) {
                            echo(request, responseObserver);
                        }
                    })).build();
        }
    }
}