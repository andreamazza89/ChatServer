package com.andreamazzarella.chat_client;

import com.andreamazzarella.chat_server.Connection;

import java.io.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

class ChatClient {

    private final OutputStream localOutputWriter;
    private final BufferedReader localInputReader;

    private final OutputStream remoteOutputWriter;
    private final BufferedReader remoteInputReader;

    ChatClient(LocalIO localIO, Connection remoteSocket) {
       this.localInputReader = new BufferedReader(new InputStreamReader(localIO.getInputStream()));
        this.localOutputWriter = new PrintStream(localIO.getOutputStream());

        try {
            this.remoteInputReader = new BufferedReader(new InputStreamReader(remoteSocket.getInputStream()));
            this.remoteOutputWriter = new PrintStream(remoteSocket.getOutputStream());
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    void startCommunication() {
        ExecutorService sendReceivePool = Executors.newFixedThreadPool(2);

        sendReceivePool.submit(() -> pipe(localInputReader, remoteOutputWriter));
        sendReceivePool.submit(() -> pipe(remoteInputReader, localOutputWriter));
    }

    private void pipe(BufferedReader reader, OutputStream writer) {
        String messageReceived;
        try {
            while ((messageReceived = reader.readLine()) != null) {
                writer.write(messageReceived.getBytes());
            }
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }
}
