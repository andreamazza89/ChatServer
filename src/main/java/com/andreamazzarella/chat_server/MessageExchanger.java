package com.andreamazzarella.chat_server;

import java.io.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MessageExchanger {

    private final BufferedReader clientOutputStream;
    private final PrintStream clientInputStream;
    private final Notifiable chatRoom;

    MessageExchanger(ClientSocket clientSocket, Notifiable chatRoom) {
        this.chatRoom = chatRoom;
        try {
            clientInputStream = new PrintStream(clientSocket.getOutputStream());
            clientOutputStream = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }

        this.start();
    }

    void forward(String message) {
        clientInputStream.println(message);
    }

    private void start() {
        ExecutorService messageListener = Executors.newSingleThreadExecutor();

        messageListener.submit(() -> {
            String message_received;
            try {
                while ((message_received = clientOutputStream.readLine()) != null) {
                    chatRoom.notifyMessageFromClient(message_received, this);
                }
            } catch (IOException e) {
                throw new UncheckedIOException(e);
            }
        });
    }
}
