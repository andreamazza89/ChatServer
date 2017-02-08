package com.andreamazzarella.chat_client;

import com.andreamazzarella.support.FakeLocalIO;
import com.andreamazzarella.support.FakeSocket;
import org.junit.Test;

import java.io.*;
import java.util.concurrent.Executors;

import static org.junit.Assert.assertEquals;

public class ChatClientShould {

    @Test
    public void printMessageReceivedFromSocketToTheLocalOutputStream() throws InterruptedException, IOException {
        FakeLocalIO localIO = new FakeLocalIO();
        FakeSocket remoteSocket = new FakeSocket();

        ChatClient chatClient = new ChatClient(localIO, remoteSocket);
        Executors.newSingleThreadExecutor().submit(chatClient::startCommunication);
        remoteSocket.newMessage("Hiya!\n");

        localIO.waitForMessageThen(1000, () -> assertEquals("Hiya!", localIO.receivedMessages()));
    }

    @Test
    public void printMessageReceivedFromLocalInputStreamToSocket() throws InterruptedException, IOException {
        FakeLocalIO localIO = new FakeLocalIO();
        FakeSocket remoteSocket = new FakeSocket();

        ChatClient chatClient = new ChatClient(localIO, remoteSocket);
        Executors.newSingleThreadExecutor().submit(chatClient::startCommunication);
        localIO.newMessage("Yo, anybody there?\n");

        remoteSocket.waitForMessageThen(1000, () -> assertEquals("Yo, anybody there?", remoteSocket.receivedMessages()));
    }

    @Test
    public void sendMessagesInBothDirectionsAtTheSameTime() {
        FakeLocalIO localIO = new FakeLocalIO();
        FakeSocket remoteSocket = new FakeSocket();

        ChatClient chatClient = new ChatClient(localIO, remoteSocket);
        Executors.newSingleThreadExecutor().submit(chatClient::startCommunication);
        localIO.newMessage("Yo, anybody there?\n");
        remoteSocket.newMessage("Yes, I am here\n");

        remoteSocket.waitForMessageThen(1000, () -> {
            assertEquals("Yo, anybody there?", remoteSocket.receivedMessages());
        });

        localIO.waitForMessageThen(1000, () -> assertEquals("Yes, I am here", localIO.receivedMessages()));
    }
}
