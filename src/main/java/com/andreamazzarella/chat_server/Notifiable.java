package com.andreamazzarella.chat_server;

import java.util.List;

public interface Notifiable {
    void notifyMessageFromClient(String message, MessageExchanger clientConnection);
    List<MessageExchanger> connectedClients();
}
