package com.andreamazzarella.chat_server;

import com.andreamazzarella.support.FakeChatRoom;
import com.andreamazzarella.support.FakeSocket;
import org.junit.Test;

import java.io.IOException;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;


public class RealUserShould {

    @Test
    public void forwardAMessageToTheClient() throws IOException {
        FakeSocket clientSocket = new FakeSocket();
        User user = new RealUser(clientSocket);

        user.forward("sample line one");
        user.forward("sample line two");

        assertThat(clientSocket.receivedMessage()).isEqualTo("sample line one\nsample line two\n");
    }

    @Test
    public void notifyTheServerWhenItReceivesAMessageFromTheClient() throws IOException, InterruptedException {
        FakeSocket clientSocket = new FakeSocket();
        User user = new RealUser(clientSocket);
        FakeChatRoom chatRoom = new FakeChatRoom();
        chatRoom.addSubscriber(user);

        Executors.newSingleThreadExecutor().submit(user::startListening);
        clientSocket.newMessage("test message\n");

        chatRoom.waitForMessageThen(1000, TimeUnit.MILLISECONDS, () -> {
            assertThat(chatRoom.receivedMessage()).isEqualTo("test message");
            assertThat(chatRoom.sentBy()).isEqualTo(user);
        });
    }

    @Test
    public void beAbleToBothReceiveAndSendMessages() throws InterruptedException, IOException {
        FakeSocket clientSocket = new FakeSocket();
        FakeChatRoom chatRoom = new FakeChatRoom();
        User user = new RealUser(clientSocket);
        chatRoom.addSubscriber(user);
        Executors.newSingleThreadExecutor().submit(user::startListening);

        user.forward("sample line one");
        clientSocket.newMessage("test message\n");
        user.forward("sample line two");

        chatRoom.waitForMessageThen(1000, TimeUnit.MILLISECONDS, () -> {
            assertThat(clientSocket.receivedMessage()).isEqualTo("sample line one\nsample line two\n");
            assertThat(chatRoom.receivedMessage()).isEqualTo("test message");
            assertThat(chatRoom.sentBy()).isEqualTo(user);
        });
    }

    @Test
    public void greetTheUser() {
        FakeSocket clientSocket = new FakeSocket();
        User user = new RealUser(clientSocket);

        user.greet();

        assertThat(clientSocket.receivedMessage()).isEqualTo("Welcome to ChattyChat\n");
    }

    @Test
    public void askTheUserName() {
        FakeSocket clientSocket = new FakeSocket();
        User user = new RealUser(clientSocket);

        Executors.newSingleThreadExecutor().submit(user::askUserName);

        clientSocket.waitForMessageThen(1000, () -> {
            assertThat(clientSocket.receivedMessage()).isEqualTo("Please enter your name\n");
        });
    }

    @Test
    public void registerTheUserName() {
        FakeSocket clientSocket = new FakeSocket();
        clientSocket.newMessage("Andrea\n");
        User user = new RealUser(clientSocket);
        user.askUserName();

        Executors.newSingleThreadExecutor().submit(() -> {
            assertThat(user.getUserName()).isEqualTo("Andrea");
        });
    }
}