package com.andreamazzarella.chat_client;

import com.andreamazzarella.chat_server.Connection;

import java.io.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ChatClient {

    private final OutputStream localOutputWriter;
    private final BufferedReader localInputReader;

    private final PrintStream remoteOutputWriter;
    private final BufferedReader remoteInputReader;

    public ChatClient(LocalIO localIO, Connection remoteSocket) {
        this.localInputReader = new BufferedReader(new InputStreamReader(localIO.getInputStream()));
        this.localOutputWriter = new PrintStream(localIO.getOutputStream());

        try {
            this.remoteInputReader = new BufferedReader(new InputStreamReader(remoteSocket.getInputStream()));
            this.remoteOutputWriter = new PrintStream(remoteSocket.getOutputStream());
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    public void startCommunication() {
        ExecutorService sendReceivePool = Executors.newFixedThreadPool(2);

        sendReceivePool.submit(() -> {
            String messageReceivedFromLocalSocket;
            try {

                while ((messageReceivedFromLocalSocket = localInputReader.readLine()) != null) {
                    remoteOutputWriter.write(messageReceivedFromLocalSocket.getBytes());
                }
            } catch (IOException e) {
                throw new UncheckedIOException(e);
            }
        });

        sendReceivePool.submit(() -> {
            String messageReceivedFromRemoteSocket;
            try {
                while ((messageReceivedFromRemoteSocket = remoteInputReader.readLine()) != null) {
                    localOutputWriter.write(messageReceivedFromRemoteSocket.getBytes());
                }
            } catch (IOException e) {
                throw new UncheckedIOException(e);
            }
        });
    }
}
