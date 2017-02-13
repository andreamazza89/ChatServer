package com.andreamazzarella.chat_server;

import com.andreamazzarella.chat_application.ChatProtocol;

import java.util.ArrayList;
import java.util.List;

public class ChatRoom implements Notifiable {

    private final ChatProtocol protocol;
    private List<User> usersSubscribedToRoom = new ArrayList<>();

    ChatRoom(ChatProtocol protocol) {
        this.protocol = protocol;
    }

    @Override
    public void notifyMessageFromClient(String rawMessage, User sender) {
        String encodedMessage = protocol.messageFrom(sender).addContent(rawMessage).encodeMessage();
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
