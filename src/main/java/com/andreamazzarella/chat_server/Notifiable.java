package com.andreamazzarella.chat_server;

public interface Notifiable {
    void messageFromClient(String message, ClientConnection clientConnection);
}
