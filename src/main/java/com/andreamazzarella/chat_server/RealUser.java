package com.andreamazzarella.chat_server;

import com.andreamazzarella.chat_application.MessageExchange;

public class RealUser implements User {

    private final ChatProtocol protocol;
    private final MessageExchange userSocket;
    private Notifiable chatRoom;
    private String userName;

    RealUser(MessageExchange clientSocket, ChatProtocol protocol) {
        this.protocol = protocol;
        this.userSocket = clientSocket;
    }

    @Override
    public void subscribeToRoom(Notifiable room) {
        this.chatRoom = room;
    }

    @Override
    public void forward(String message) {
        userSocket.sendMessage(message);
    }

    @Override
    public void startConversation() {
        String message_received;
        while ((message_received = userSocket.readMessage()) != null) {
            chatRoom.notifyMessageFromClient(message_received, this);
        }
    }

    @Override
    public String getUserName() {
        return userName;
    }

    @Override
    public void askUserName() {
        String encodedQuestion = protocol.addContent("Please enter your name").encodeMessage();
        userSocket.sendMessage(encodedQuestion);
        userName = userSocket.readMessage();
    }

    @Override
    public void greet() {
        String encodedGreeting = protocol.addContent("Welcome to ChattyChat").encodeMessage();
        userSocket.sendMessage(encodedGreeting);
    }
}
