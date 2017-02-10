package com.andreamazzarella.chat_client;

import com.andreamazzarella.chat_server.Connection;

import java.io.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

class ChatClient {
    private final LocalIO localIO;
    private final BufferedReader localInputReader;

    private final PrintStream remoteOutputWriter;
    private final BufferedReader remoteInputReader;

    ChatClient(LocalIO localIO, Connection remoteSocket) {
       this.localInputReader = new BufferedReader(new InputStreamReader(localIO.getInputStream()));
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

        sendReceivePool.submit(() -> sendOutgoingMessages(localInputReader, remoteOutputWriter));
        sendReceivePool.submit(() -> decodeIncomingMessages());
    }

    private void sendOutgoingMessages(BufferedReader reader, PrintStream writer) {
        String messageReceived;
        try {
            while ((messageReceived = reader.readLine()) != null) {
                writer.println(messageReceived);
            }
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    private void decodeIncomingMessages() {
        String rawMessageReceived;
        try {
            while ((rawMessageReceived = remoteInputReader.readLine()) != null) {
                localIO.addMessage(rawMessageReceived);
            }
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

}
