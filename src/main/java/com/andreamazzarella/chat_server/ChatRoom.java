package com.andreamazzarella.chat_server;

import java.util.ArrayList;
import java.util.List;

public class ChatRoom implements Notifiable{

    private List<MessageExchanger> clientsConnected = new ArrayList<>();

    @Override
    public void messageFromClientNotification(String message, MessageExchanger messageSender) {
        for (MessageExchanger messageExchanger : clientsConnected) {
            if (messageExchanger != messageSender) {
                messageExchanger.forward(message);
            }
        }
    }

    void addMessageExchanger(MessageExchanger messageExchanger) {
        clientsConnected.add(messageExchanger);
    }
}
