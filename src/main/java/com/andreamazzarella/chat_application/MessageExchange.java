package com.andreamazzarella.chat_application;

public interface MessageExchange {
    void sendMessage(String message);
    String readMessage();
}
