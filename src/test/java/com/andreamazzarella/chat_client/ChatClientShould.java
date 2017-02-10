package com.andreamazzarella.chat_client;

import com.andreamazzarella.support.FakeLocalIO;
import com.andreamazzarella.support.FakeSocket;
import org.junit.Test;

import java.io.*;
import java.util.concurrent.Executors;

import static org.junit.Assert.assertEquals;

public class ChatClientShould {

    @Test
    public void passMessageReceivedFromSocketToLocalIO() throws InterruptedException, IOException {
        FakeLocalIO localIO = new FakeLocalIO();
        FakeSocket remoteSocket = new FakeSocket();

        ChatClient chatClient = new ChatClient(localIO, remoteSocket);
        Executors.newSingleThreadExecutor().submit(chatClient::startCommunication);
        remoteSocket.newMessage("Hiya!\n");

        localIO.waitForMessageThen(1000, () -> assertEquals("Hiya!\n", localIO.receivedMessages()));
    }

    @Test
    public void passMessageReceivedFromLocalIOToSocket() throws InterruptedException, IOException {
        FakeLocalIO localIO = new FakeLocalIO();
        FakeSocket remoteSocket = new FakeSocket();

        ChatClient chatClient = new ChatClient(localIO, remoteSocket);
        Executors.newSingleThreadExecutor().submit(chatClient::startCommunication);
        localIO.newMessage("Yo, anybody there?\n");

        remoteSocket.waitForMessageThen(1000, () -> assertEquals("Yo, anybody there?\n", remoteSocket.receivedMessage()));
    }

    @Test
    public void passMessagesInBothDirectionsAtTheSameTime() {
        FakeLocalIO localIO = new FakeLocalIO();
        FakeSocket remoteSocket = new FakeSocket();

        ChatClient chatClient = new ChatClient(localIO, remoteSocket);
        Executors.newSingleThreadExecutor().submit(chatClient::startCommunication);
        localIO.newMessage("Yo, anybody there?\n");
        remoteSocket.newMessage("Yes, I am here\n");

        remoteSocket.waitForMessageThen(1000, () -> {
            assertEquals("Yo, anybody there?\n", remoteSocket.receivedMessage());
        });

        localIO.waitForMessageThen(1000, () -> assertEquals("Yes, I am here\n", localIO.receivedMessages()));
    }
}
