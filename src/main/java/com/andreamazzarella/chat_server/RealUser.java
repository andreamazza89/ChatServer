package com.andreamazzarella.chat_server;

import java.io.*;

public class RealUser implements User {

    private final BufferedReader clientOutputStream;
    private final PrintStream clientInputStream;
    private final ChatProtocol protocol;
    private Notifiable chatRoom;
    private String userName;

    RealUser(Connection clientSocket, ChatProtocol protocol) {
        this.protocol = protocol;
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
    public void startConversation() {
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
        String encodedQuestion = protocol.addContent("Please enter your name").encodeMessage();
        clientInputStream.println(encodedQuestion);
        try {
            userName = clientOutputStream.readLine();
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    @Override
    public void greet() {
        String encodedGreeting = protocol.addContent("Welcome to ChattyChat").encodeMessage();
        clientInputStream.println(encodedGreeting);
    }
}
