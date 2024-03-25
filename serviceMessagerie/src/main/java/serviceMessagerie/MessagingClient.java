package serviceMessagerie;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import messaging.MessagingServiceGrpc;
import messaging.SendMessageRequest;
import messaging.SendMessageResponse;

public class MessagingClient {
    private final ManagedChannel channel;
    private final MessagingServiceGrpc.MessagingServiceBlockingStub blockingStub;

    public MessagingClient(String host, int port) {
        this(ManagedChannelBuilder.forAddress(host, port).usePlaintext());
    }

    public MessagingClient(ManagedChannelBuilder<?> channelBuilder) {
        channel = channelBuilder.build();
        blockingStub = MessagingServiceGrpc.newBlockingStub(channel);
    }

    public void sendMessage(String sender, String recipient, String message) {
        SendMessageRequest request = SendMessageRequest.newBuilder()
                .setSender(sender)
                .setRecipient(recipient)
                .setMessage(message)
                .build();
        SendMessageResponse response = blockingStub.sendMessage(request);
        System.out.println("Response from server: " + response.getStatus());
    }

    public void shutdown() throws InterruptedException {
        channel.shutdown().awaitTermination(5, java.util.concurrent.TimeUnit.SECONDS);
    }

    public static void main(String[] args) throws Exception {
        MessagingClient client = new MessagingClient("localhost", 50051);
        try {
            client.sendMessage("User1", "User2", "Hello, how are you?");
        } finally {
            client.shutdown();
        }
    }
}

