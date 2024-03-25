package serviceMessagerie;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.stub.StreamObserver;
import messaging.MessagingServiceGrpc;
import messaging.SendMessageRequest;
import messaging.SendMessageResponse;

import java.io.IOException;

public class MessagingServer {
    private final int port;
    private final Server server;

    public MessagingServer(int port) {
        this.port = port;
        this.server = ServerBuilder.forPort(port)
                .addService(new MessagingServiceImpl())
                .build();
    }

    public void start() throws IOException {
        server.start();
        System.out.println("Server started, listening on " + port);
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.err.println("*** shutting down gRPC server since JVM is shutting down");
            MessagingServer.this.stop();
            System.err.println("*** server shut down");
        }));
    }

    public void stop() {
        if (server != null) {
            server.shutdown();
        }
    }

    private void blockUntilShutdown() throws InterruptedException {
        if (server != null) {
            server.awaitTermination();
        }
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        MessagingServer server = new MessagingServer(50051);
        server.start();
        server.blockUntilShutdown();
    }

    static class MessagingServiceImpl extends MessagingServiceGrpc.MessagingServiceImplBase {
        @Override
        public void sendMessage(SendMessageRequest request, StreamObserver<SendMessageResponse> responseObserver) {
            // Logique pour envoyer le message
            String status = "Message sent from " + request.getSender() + " to " + request.getRecipient() + ": " + request.getMessage();
            SendMessageResponse response = SendMessageResponse.newBuilder().setStatus(status).build();
            responseObserver.onNext(response);
            responseObserver.onCompleted();
        }
    }
}
