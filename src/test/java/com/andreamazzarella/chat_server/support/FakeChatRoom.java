package com.andreamazzarella.chat_server.support;

import com.andreamazzarella.chat_server.MessageExchanger;
import com.andreamazzarella.chat_server.Notifiable;

import java.util.ArrayList;
import java.util.List;

public class FakeChatRoom implements Notifiable {
    private String receivedMessage;
    private MessageExchanger clientConnection;
    public boolean messageWasReceived = false;

    @Override
    public void notifyMessageFromClient(String message, MessageExchanger clientConnection) {
        messageWasReceived = true;
        this.receivedMessage = message;
        this.clientConnection = clientConnection;
    }

    @Override
    public List<MessageExchanger> connectedClients() {
        return new ArrayList<>();
    }

    public String receivedMessage() {
        return receivedMessage;
    }

    public MessageExchanger sentBy() {
        return clientConnection;
    }

}
