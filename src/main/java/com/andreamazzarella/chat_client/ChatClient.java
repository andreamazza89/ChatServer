package com.andreamazzarella.chat_client;

import com.andreamazzarella.chat_server.Connection;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

class ChatClient {
    private final LocalIO localIO;

    private final Connection remoteSocket;

    ChatClient(LocalIO localIO, Connection remoteSocket) {
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
        while ((messageReceived = localIO.readLine()) != null) {
            remoteSocket.sendMessage(messageReceived);
        }
    }

    private void decodeIncomingMessages() {
        String rawMessageReceived;
        while ((rawMessageReceived = remoteSocket.readLine()) != null) {
            localIO.displayMessage(rawMessageReceived);
        }
    }
}
