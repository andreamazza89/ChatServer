package com.andreamazzarella.support;

import com.andreamazzarella.chat_server.MessageExchanger;
import com.andreamazzarella.chat_server.Notifiable;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class FakeChatRoom implements Notifiable {
    private String receivedMessage;
    private MessageExchanger clientConnection;
    private CountDownLatch waitForMessageNotification = new CountDownLatch(1);

    @Override
    public void notifyMessageFromClient(String message, MessageExchanger clientConnection) {
        waitForMessageNotification.countDown();

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

    public void waitForMessageThen(long timeout, TimeUnit timeUnit, Runnable callback) {
        try {
            waitForMessageNotification.await(timeout, timeUnit);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        callback.run();
    }

}
