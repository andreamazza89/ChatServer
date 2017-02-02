package com.andreamazzarella.chat_server;

import com.andreamazzarella.chat_server.support.FakeClientSocket;
import com.andreamazzarella.chat_server.support.FakeFIND_ME_A_GOOD_NAME;
import org.junit.Test;

import java.io.IOException;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;


public class ClientHandlerShould {

    @Test
    public void forwardAMessageToTheClient() throws IOException {
        FakeClientSocket clientSocket = new FakeClientSocket();
        ClientHandler clientHandler = new ClientHandler(clientSocket, new FakeFIND_ME_A_GOOD_NAME());

        clientHandler.forward("sample line one");
        clientHandler.forward("sample line two");

        assertThat(clientSocket.receivedMessages()).isEqualTo("sample line one\nsample line two\n");
    }

    @Test
    public void notifyTheServerWhenItReceivesAMessageFromTheClient() throws IOException {
        FakeClientSocket clientSocket = new FakeClientSocket();
        clientSocket.setMessage("test message");

        FakeFIND_ME_A_GOOD_NAME fIND_A_GOOD_NAME_FOR_THIS_PLEASE = new FakeFIND_ME_A_GOOD_NAME();
        ClientHandler clientHandler = new ClientHandler(clientSocket, fIND_A_GOOD_NAME_FOR_THIS_PLEASE);

        clientHandler.start();

        assertThat(fIND_A_GOOD_NAME_FOR_THIS_PLEASE.receivedMessage()).isEqualTo("test message");
        assertThat(fIND_A_GOOD_NAME_FOR_THIS_PLEASE.sentBy()).isEqualTo(clientSocket);
    }
}