package com.andreamazzarella.chat_server;

import java.util.ArrayList;
import java.util.List;

public class ChatRoom implements Notifiable {

    private final CommunicationProtocol protocol;
    private List<User> usersSubscribedToRoom = new ArrayList<>();

    public ChatRoom(CommunicationProtocol protocol) {
        this.protocol = protocol;
    }

    @Override
    public void notifyMessageFromClient(String rawMessage, User sender) {
        String encodedMessage = protocol.messageFrom(sender).withContent(rawMessage).encode();
        for (User user : usersSubscribedToRoom) {
            if (user != sender) {
                user.forward(encodedMessage);
            }
        }
    }

    @Override
    public void addSubscriber(User user) {
        usersSubscribedToRoom.add(user);
        user.subscribeToRoom(this);
    }

}
