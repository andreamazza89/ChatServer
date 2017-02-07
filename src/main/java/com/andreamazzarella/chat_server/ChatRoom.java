package com.andreamazzarella.chat_server;

import java.util.ArrayList;
import java.util.List;

public class ChatRoom implements Notifiable {

    private List<MessageExchanger> exchangersSubscribedToRoom = new ArrayList<>();

    @Override
    public void notifyMessageFromClient(String message, MessageExchanger sender) {
        for (MessageExchanger messageExchanger : exchangersSubscribedToRoom) {
            if (messageExchanger != sender) {
                messageExchanger.forward(message);
            }
        }
    }

    @Override
    public List<MessageExchanger> connectedClients() {
        return exchangersSubscribedToRoom;
    }

    void addSubscriber(MessageExchanger messageExchanger) {
        exchangersSubscribedToRoom.add(messageExchanger);
    }

}
