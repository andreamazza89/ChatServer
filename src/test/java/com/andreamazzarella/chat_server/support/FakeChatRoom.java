package com.andreamazzarella.chat_server.support;

import com.andreamazzarella.chat_server.MessageExchanger;
import com.andreamazzarella.chat_server.Notifiable;

public class FakeChatRoom implements Notifiable {
    private String receivedMessage;
    private MessageExchanger clientConnection;
    public boolean messageWasReceived = false;

    @Override
    public void messageFromClientNotification(String message, MessageExchanger clientConnection) {
        messageWasReceived = true;
        this.receivedMessage = message;
        this.clientConnection = clientConnection;
    }

    public String receivedMessage() {
        return receivedMessage;
    }

    public MessageExchanger sentBy() {
        return clientConnection;
    }

}
