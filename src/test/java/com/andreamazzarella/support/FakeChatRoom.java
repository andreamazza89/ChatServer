package com.andreamazzarella.support;

import com.andreamazzarella.chat_server.User;
import com.andreamazzarella.chat_server.Notifiable;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class FakeChatRoom implements Notifiable {
    private String receivedMessage;
    private User user;
    private CountDownLatch waitForMessageNotification = new CountDownLatch(1);

    @Override
    public void notifyMessageFromClient(String message, User user) {
        waitForMessageNotification.countDown();

        this.receivedMessage = message;
        this.user = user;
    }

    @Override
    public void addSubscriber(User user) {
        user.subscribeToRoom(this);
    }

    public String receivedMessage() {
        return receivedMessage;
    }

    public User sentBy() {
        return user;
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
