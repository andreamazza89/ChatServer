package com.andreamazzarella.chat_client;

import com.andreamazzarella.chat_server.Connection;

import java.io.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

class ChatClient {
    private final LocalIO localIO;

    private final PrintStream remoteOutputWriter;
    private final BufferedReader remoteInputReader;

    ChatClient(LocalIO localIO, Connection remoteSocket) {
       this.localIO = localIO;

        try {
            this.remoteInputReader = new BufferedReader(new InputStreamReader(remoteSocket.getInputStream()));
            this.remoteOutputWriter = new PrintStream(remoteSocket.getOutputStream());
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    void startCommunication() {
        ExecutorService sendReceivePool = Executors.newFixedThreadPool(2);

        sendReceivePool.submit(() -> sendOutgoingMessages());
        sendReceivePool.submit(() -> decodeIncomingMessages());
    }

    private void sendOutgoingMessages() {
        String messageReceived;
        while ((messageReceived = localIO.readLine()) != null) {
            remoteOutputWriter.println(messageReceived);
        }
    }

    private void decodeIncomingMessages() {
        String rawMessageReceived;
        try {
            while ((rawMessageReceived = remoteInputReader.readLine()) != null) {
                localIO.addMessage(rawMessageReceived);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
