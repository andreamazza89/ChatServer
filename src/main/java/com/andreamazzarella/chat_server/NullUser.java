package com.andreamazzarella.chat_server;

public class NullUser implements User {
    @Override
    public void greet() {
        throw new RuntimeException("Method not implemented");
    }

    @Override
    public void askUserName() {
        throw new RuntimeException("Method not implemented");
    }

    @Override
    public void forward(String message) {
        throw new RuntimeException("Method not implemented");
    }

    @Override
    public void subscribeToRoom(Notifiable room) {
        throw new RuntimeException("Method not implemented");
    }

    @Override
    public void startConversation() {
        throw new RuntimeException("Method not implemented");
    }

    @Override
    public String getUserName() {
        return "";
    }
}
