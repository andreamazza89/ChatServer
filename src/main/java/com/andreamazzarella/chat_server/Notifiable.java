package com.andreamazzarella.chat_server;

public interface Notifiable {
    void messageFromClientNotification(String message, ClientSocket clientConnection);
}
