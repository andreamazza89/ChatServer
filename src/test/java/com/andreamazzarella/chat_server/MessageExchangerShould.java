package com.andreamazzarella.chat_server;

import com.andreamazzarella.chat_server.support.FakeClientSocket;
import com.andreamazzarella.chat_server.support.FakeChatRoom;
import org.junit.Test;

import java.io.*;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;


public class MessageExchangerShould {

    @Test
    public void forwardAMessageToTheClient() throws IOException {
        FakeClientSocket clientSocket = new FakeClientSocket();
        MessageExchanger messageExchanger = new MessageExchanger(clientSocket, new FakeChatRoom());

        messageExchanger.forward("sample line one");
        messageExchanger.forward("sample line two");

        assertThat(clientSocket.receivedMessages()).isEqualTo("sample line one\nsample line two\n");
    }

    @Test
    public void notifyTheServerWhenItReceivesAMessageFromTheClient() throws IOException, InterruptedException {
        FakeClientSocket clientSocket = new FakeClientSocket();
        FakeChatRoom chatRoom = new FakeChatRoom();
        MessageExchanger messageExchanger = new MessageExchanger(clientSocket, chatRoom);
        Executors.newSingleThreadExecutor().submit(() -> messageExchanger.startListening());

        clientSocket.newMessage("test message\n");

        chatRoom.waitForMessageThen(1000, TimeUnit.MILLISECONDS, () -> {
            assertThat(chatRoom.receivedMessage()).isEqualTo("test message");
            assertThat(chatRoom.sentBy()).isEqualTo(messageExchanger);
        });
    }

    @Test
    public void beAbleToBothReceiveAndSendMessages() throws InterruptedException, IOException {
        FakeClientSocket clientSocket = new FakeClientSocket();
        FakeChatRoom chatRoom = new FakeChatRoom();
        MessageExchanger messageExchanger = new MessageExchanger(clientSocket, chatRoom);
        Executors.newSingleThreadExecutor().submit(() -> messageExchanger.startListening());

        messageExchanger.forward("sample line one");
        clientSocket.newMessage("test message\n");
        messageExchanger.forward("sample line two");

        chatRoom.waitForMessageThen(1000, TimeUnit.MILLISECONDS, () -> {
            assertThat(clientSocket.receivedMessages()).isEqualTo("sample line one\nsample line two\n");
            assertThat(chatRoom.receivedMessage()).isEqualTo("test message");
            assertThat(chatRoom.sentBy()).isEqualTo(messageExchanger);
        });
    }
}