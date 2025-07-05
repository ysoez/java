package grpc.service;

import grpc.echo.EchoReply;
import grpc.echo.EchoRequest;
import grpc.echo.EchoServiceGrpc;
import io.grpc.stub.StreamObserver;

public class DefaultEchoService extends EchoServiceGrpc.EchoServiceImplBase {

    @Override
    public void echo(EchoRequest req, StreamObserver<EchoReply> responseObserver) {
        var reply = EchoReply.newBuilder().setMessage(req.getMessage()).build();
        responseObserver.onNext(reply);
        responseObserver.onCompleted();
    }

}
