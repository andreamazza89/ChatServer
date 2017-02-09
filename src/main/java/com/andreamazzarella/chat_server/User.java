package com.andreamazzarella.chat_server;

public interface User {
    void greet();
    void askUserName();
    void forward(String message);
    void subscribeToRoom(Notifiable room);
    void startConversation();
    String getUserName();
}
