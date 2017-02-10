package com.andreamazzarella.chat_client;

public interface LocalIO {
    void addMessage(String message);
    String readLine();
}
