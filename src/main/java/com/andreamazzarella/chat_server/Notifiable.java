package com.andreamazzarella.chat_server;

public interface Notifiable {
    void notifyDataReceivedFromClient(String data, User user);
    void addSubscriber(User user);
}
