package com.andreamazzarella.chat_server;

import com.andreamazzarella.chat_application.ChatProtocol;
import com.andreamazzarella.chat_application.MessageExchange;

import java.util.Optional;

public class RealUser implements User {

    private final static String GREETING = "Welcome to ChattyChat";
    private final static String ASK_FOR_NAME = "Please enter your name";

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
        String dataReceived;
        while ((dataReceived = userSocket.readMessage()) != null) {
            chatRoom.notifyDataReceivedFromClient(dataReceived, this);
        }
    }

    @Override
    public String getUserName() {
        return userName;
    }

    @Override
    public void askUserName() {
        Message askNameMessage = new Message(Optional.empty(), ASK_FOR_NAME);
        String encodedQuestion = protocol.encodeMessage(askNameMessage);
        userSocket.sendMessage(encodedQuestion);
        userName = userSocket.readMessage();
    }

    @Override
    public void greet() {
        Message greetingMessage = new Message(Optional.empty(), GREETING);
        String encodedGreeting = protocol.encodeMessage(greetingMessage);
        userSocket.sendMessage(encodedGreeting);
    }
}
