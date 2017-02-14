package com.andreamazzarella.chat_server;

import com.andreamazzarella.chat_application.ChatProtocol;
import com.andreamazzarella.chat_application.Message;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ChatRoom implements Notifiable {

    private final ChatProtocol protocol;
    private final MessageRepository repository;
    private List<User> usersSubscribedToRoom = new ArrayList<>();

    public ChatRoom(ChatProtocol protocol, MessageRepository repository) {
        this.protocol = protocol;
        this.repository = repository;
    }

    @Override
    public void notifyMessageFromClient(String rawMessage, User sender) {
        Message message = new Message(Optional.of(sender), rawMessage);
        repository.add(message);

        String encodedMessage = protocol.encodeMessage(message);
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

        List<Message> messageHistory = repository.all();
        for (Message message : messageHistory) {
            user.forward(protocol.encodeMessage(message));
        }
    }
}
