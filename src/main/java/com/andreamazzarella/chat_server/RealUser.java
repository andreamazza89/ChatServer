package com.andreamazzarella.chat_server;

import java.io.*;

public class RealUser implements User {

    private final BufferedReader clientOutputStream;
    private final PrintStream clientInputStream;
    private Notifiable chatRoom;
    private String userName;

    RealUser(Connection clientSocket) {
        try {
            clientInputStream = new PrintStream(clientSocket.getOutputStream());
            clientOutputStream = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    @Override
    public void subscribeToRoom(Notifiable room) {
        this.chatRoom = room;
    }

    @Override
    public void forward(String message) {
        clientInputStream.println(message);
    }

    @Override
    public void startListening() {
        String message_received;
        try {
            while ((message_received = clientOutputStream.readLine()) != null) {
                chatRoom.notifyMessageFromClient(message_received, this);
            }
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    @Override
    public String getUserName() {
        return userName;
    }

    @Override
    public void askUserName() {
        clientInputStream.println("Please enter your name");
        try {
            userName = clientOutputStream.readLine();
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    @Override
    public void greet() {
        clientInputStream.println("Welcome to ChattyChat");
    }
}
