package com.andreamazzarella.chat_server;

import java.io.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

class MessageExchanger {
    private final BufferedReader clientOutputStream;
    private final PrintStream clientInputStream;
    private final ClientSocket clientSocket;
    private final Notifiable chatRoom;

    MessageExchanger(ClientSocket clientSocket, Notifiable chatRoom) {
        this.clientSocket = clientSocket;
        this.chatRoom = chatRoom;
        try {
            clientInputStream = new PrintStream(clientSocket.getOutputStream());
            clientOutputStream = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    void forward(String message) {
        clientInputStream.println(message);
    }

    void run() {
        ExecutorService messageListener = Executors.newSingleThreadExecutor();

        messageListener.submit(() -> {
            String message_received;
            try {
                while ((message_received = clientOutputStream.readLine()) != null) {
                    chatRoom.messageFromClientNotification(message_received, clientSocket);
                }
            } catch (IOException e) {
                throw new UncheckedIOException(e);
            }
        });
    }
}
