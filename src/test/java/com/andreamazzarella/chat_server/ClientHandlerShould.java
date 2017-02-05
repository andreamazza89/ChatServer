package com.andreamazzarella.chat_server;

import com.andreamazzarella.chat_server.support.FakeClientSocket;
import com.andreamazzarella.chat_server.support.FakeChatRoom;
import org.junit.Test;

import java.io.*;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;


public class ClientHandlerShould {

    @Test
    public void forwardAMessageToTheClient() throws IOException {
        FakeClientSocket clientSocket = new FakeClientSocket();
        MessageExchanger clientHandler = new MessageExchanger(clientSocket, new FakeChatRoom());

        clientHandler.forward("sample line one");
        clientHandler.forward("sample line two");

        assertThat(clientSocket.receivedMessages()).isEqualTo("sample line one\nsample line two\n");
    }

    @Test
    public void notifyTheServerWhenItReceivesAMessageFromTheClient() throws IOException, InterruptedException {
        FakeClientSocket clientSocket = new FakeClientSocket();

        FakeChatRoom fakeChatRoom = new FakeChatRoom();
        MessageExchanger messageExchanger = new MessageExchanger(clientSocket, fakeChatRoom);

        messageExchanger.run();
        clientSocket.newMessage("test message\n");

        while (!fakeChatRoom.messageWasReceived) {
            Thread.sleep(1);
        }

        assertThat(fakeChatRoom.receivedMessage()).isEqualTo("test message");
        assertThat(fakeChatRoom.sentBy()).isEqualTo(clientSocket);
    }

    @Test
    public void beAbleToBothReceiveAndSendMessages() throws InterruptedException, IOException {
        FakeClientSocket clientSocket = new FakeClientSocket();
        FakeChatRoom fakeChatRoom = new FakeChatRoom();

        MessageExchanger messageExchanger = new MessageExchanger(clientSocket, fakeChatRoom);

        messageExchanger.run();
        messageExchanger.forward("sample line one");
        clientSocket.newMessage("test message\n");

        while (!fakeChatRoom.messageWasReceived) {
            Thread.sleep(1);
        }

        messageExchanger.forward("sample line two");

        assertThat(clientSocket.receivedMessages()).isEqualTo("sample line one\nsample line two\n");
        assertThat(fakeChatRoom.receivedMessage()).isEqualTo("test message");
        assertThat(fakeChatRoom.sentBy()).isEqualTo(clientSocket);
    }
}