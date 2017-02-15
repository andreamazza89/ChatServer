package com.andreamazzarella.chat_client;

import com.andreamazzarella.chat_application.DataExchange;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

class ChatClient {
    private final DataExchange localIO;
    private final DataExchange remoteSocket;

    ChatClient(DataExchange localIO, DataExchange remoteSocket) {
       this.localIO = localIO;
       this.remoteSocket = remoteSocket;
    }

    void startCommunication() {
        ExecutorService sendReceivePool = Executors.newFixedThreadPool(2);

        sendReceivePool.submit(() -> pipeMessages(localIO, remoteSocket));
        sendReceivePool.submit(() -> pipeMessages(remoteSocket, localIO));
    }

    private void pipeMessages(DataExchange from, DataExchange to) {
        String messageReceived;
        while ((messageReceived = from.readData()) != null) {
            to.sendData(messageReceived);
        }
    }
}
