package com.andreamazzarella.chat_client;

import com.andreamazzarella.support.FakeMessageExchange;
import org.junit.Test;

import java.io.*;
import java.util.concurrent.Executors;

import static org.junit.Assert.assertEquals;

public class ChatClientShould {

    @Test
    public void passMessageReceivedFromSocketToLocalIO() throws InterruptedException, IOException {
        FakeMessageExchange localIO = new FakeMessageExchange();
        FakeMessageExchange remoteSocket = new FakeMessageExchange();

        ChatClient chatClient = new ChatClient(localIO, remoteSocket);
        Executors.newSingleThreadExecutor().submit(chatClient::startCommunication);
        remoteSocket.newMessage("Hiya!\n");

        localIO.waitForMessageThen(1000, () -> assertEquals("Hiya!", localIO.receivedMessage()));
    }

    @Test
    public void passMessageReceivedFromLocalIOToSocket() throws InterruptedException, IOException {
        FakeMessageExchange localIO = new FakeMessageExchange();
        FakeMessageExchange remoteSocket = new FakeMessageExchange();

        ChatClient chatClient = new ChatClient(localIO, remoteSocket);
        Executors.newSingleThreadExecutor().submit(chatClient::startCommunication);
        localIO.newMessage("Yo, anybody there?\n");

        remoteSocket.waitForMessageThen(1000, () -> assertEquals("Yo, anybody there?", remoteSocket.receivedMessage()));
    }

    @Test
    public void passMessagesInBothDirectionsAtTheSameTime() {
        FakeMessageExchange localIO = new FakeMessageExchange();
        FakeMessageExchange remoteSocket = new FakeMessageExchange();

        ChatClient chatClient = new ChatClient(localIO, remoteSocket);
        Executors.newSingleThreadExecutor().submit(chatClient::startCommunication);
        localIO.newMessage("Yo, anybody there?\n");
        remoteSocket.newMessage("Yes, I am here\n");

        remoteSocket.waitForMessageThen(1000, () -> {
            assertEquals("Yo, anybody there?", remoteSocket.receivedMessage());
        });

        localIO.waitForMessageThen(1000, () -> assertEquals("Yes, I am here", localIO.receivedMessage()));
    }
}
