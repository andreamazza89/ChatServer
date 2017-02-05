package com.andreamazzarella.chat_server.support;

import com.andreamazzarella.chat_server.ClientSocket;
import com.andreamazzarella.chat_server.Notifiable;

public class FakeChatRoom implements Notifiable {
    private String receivedMessage;
    private ClientSocket clientConnection;
    public boolean messageWasReceived = false;

    @Override
    public void messageFromClientNotification(String message, ClientSocket clientConnection) {
        messageWasReceived = true;
        this.receivedMessage = message;
        this.clientConnection = clientConnection;
    }

    public String receivedMessage() {
        return receivedMessage;
    }

    public ClientSocket sentBy() {
        return clientConnection;
    }

}
