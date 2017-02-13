package com.andreamazzarella.chat_server;

public interface Notifiable {
    void notifyMessageFromClient(String message, User user);
    void addSubscriber(User user);
}
