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

        sendReceivePool.submit(() -> pipeMessages(localIO, remoteSocket));
        sendReceivePool.submit(() -> pipeMessages(remoteSocket, localIO));
    }

    private void pipeMessages(MessageExchange from, MessageExchange to) {
        String messageReceived;
        while ((messageReceived = from.readMessage()) != null) {
            to.sendMessage(messageReceived);
        }
    }
}
