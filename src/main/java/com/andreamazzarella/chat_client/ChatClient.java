package com.andreamazzarella.chat_client;

import com.andreamazzarella.chat_application.MessageExchange;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

class ChatClient {
    private final MessageExchange localIO;
    private final MessageExchange remoteSocket;

    ChatClient(MessageExchange localIO, MessageExchange remoteSocket) {
       this.localIO = localIO;
       this.remoteSocket = remoteSocket;
    }

    void startCommunication() {
        ExecutorService sendReceivePool = Executors.newFixedThreadPool(2);

        sendReceivePool.submit(this::sendOutgoingMessages);
        sendReceivePool.submit(this::decodeIncomingMessages);
    }

    private void sendOutgoingMessages() {
        String messageReceived;
        while ((messageReceived = localIO.readMessage()) != null) {
            remoteSocket.sendMessage(messageReceived);
        }
    }

    private void decodeIncomingMessages() {
        String rawMessageReceived;
        while ((rawMessageReceived = remoteSocket.readMessage()) != null) {
            localIO.sendMessage(rawMessageReceived);
        }
    }
}
