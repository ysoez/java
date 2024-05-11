package grpc.shared;

import grpc.echo.EchoReply;
import grpc.echo.EchoRequest;
import grpc.echo.EchoServiceGrpc;
import io.grpc.stub.StreamObserver;

public class EchoService extends EchoServiceGrpc.EchoServiceImplBase {

    @Override
    public void echo(EchoRequest req, StreamObserver<EchoReply> responseObserver) {
        var reply = EchoReply.newBuilder().setMessage("server: " + req.getMessage()).build();
        responseObserver.onNext(reply);
        responseObserver.onCompleted();
    }

}