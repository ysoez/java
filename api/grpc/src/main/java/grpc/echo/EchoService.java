package grpc.echo;

import io.grpc.stub.StreamObserver;

class EchoService extends EchoServiceGrpc.EchoServiceImplBase {

    @Override
    public void echo(EchoRequest req, StreamObserver<EchoReply> responseObserver) {
        var reply = EchoReply.newBuilder().setMessage(req.getMessage()).build();
        responseObserver.onNext(reply);
        responseObserver.onCompleted();
    }

}
