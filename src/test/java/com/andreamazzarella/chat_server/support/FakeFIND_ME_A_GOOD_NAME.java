package com.andreamazzarella.chat_server.support;

import com.andreamazzarella.chat_server.ClientConnection;
import com.andreamazzarella.chat_server.Notifiable;

public class FakeFIND_ME_A_GOOD_NAME implements Notifiable {
    private String receivedMessage;
    private ClientConnection clientConnection;

    @Override
    public void messageFromClient(String message, ClientConnection clientConnection) {
        this.receivedMessage = message;
        this.clientConnection = clientConnection;
    }

    public String receivedMessage() {
        return receivedMessage;
    }

    public ClientConnection sentBy() {
        return clientConnection;
    }
}
