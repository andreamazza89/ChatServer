package com.andreamazzarella.support;

import com.andreamazzarella.chat_server.Notifiable;
import com.andreamazzarella.chat_server.User;

public class FakeUser implements User {

    private String userName;
    private String receivedMessage = "";
    private boolean subscribeToRoomWasCalled;
    private Notifiable subscribeToRoomWasCalledWith;

    public FakeUser(String userName) {
        this.userName = userName;
    }

    @Override
    public void greet() {
    }

    @Override
    public void askUserName() {
    }

    @Override
    public void forward(String message) {
        receivedMessage = message;
    }

    @Override
    public void subscribeToRoom(Notifiable room) {
        subscribeToRoomWasCalled = true;
        subscribeToRoomWasCalledWith = room;
    }

    @Override
    public void startConversation() {
    }

    @Override
    public String getUserName() {
        return userName;
    }

    public String receivedMessage() {
        return receivedMessage;
    }

    public boolean subscribeToRoomWasCalled() {
        return subscribeToRoomWasCalled;
    }

    public Notifiable subscribeToRoomWasCalledWith() {
        return subscribeToRoomWasCalledWith;
    }
}
