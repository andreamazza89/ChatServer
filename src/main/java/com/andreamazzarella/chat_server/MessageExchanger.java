package com.andreamazzarella.chat_server;

import java.io.*;

public class MessageExchanger {

    private final BufferedReader clientOutputStream;
    private final PrintStream clientInputStream;
    private final Notifiable chatRoom;

    MessageExchanger(Connection clientSocket, Notifiable chatRoom) {
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

    void startListening() {
        String message_received;
        try {
            while ((message_received = clientOutputStream.readLine()) != null) {
                chatRoom.notifyMessageFromClient(message_received, this);
            }
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }
}
