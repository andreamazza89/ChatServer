package com.andreamazzarella.chat_server;

import com.andreamazzarella.support.FakeChatRoom;
import com.andreamazzarella.support.FakeMessageExchange;
import org.junit.Test;

import java.io.IOException;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;


public class RealUserShould {

    @Test
    public void forwardAMessageToTheClient() throws IOException {
        FakeMessageExchange clientSocket = new FakeMessageExchange();
        User user = new RealUser(clientSocket, new RealChatProtocol());

        user.forward("sample line one");
        user.forward("sample line two");

        assertThat(clientSocket.receivedMessage()).isEqualTo("sample line onesample line two");
    }

    @Test
    public void notifyTheServerWhenItReceivesAMessageFromTheClient() throws IOException, InterruptedException {
        FakeMessageExchange clientSocket = new FakeMessageExchange();
        User user = new RealUser(clientSocket, new RealChatProtocol());
        FakeChatRoom chatRoom = new FakeChatRoom();
        chatRoom.addSubscriber(user);

        Executors.newSingleThreadExecutor().submit(user::startConversation);
        clientSocket.newMessage("test message\n");

        chatRoom.waitForMessageThen(1000, TimeUnit.MILLISECONDS, () -> {
            assertThat(chatRoom.receivedMessage()).isEqualTo("test message");
            assertThat(chatRoom.sentBy()).isEqualTo(user);
        });
    }

    @Test
    public void beAbleToBothReceiveAndSendMessages() throws InterruptedException, IOException {
        FakeMessageExchange clientSocket = new FakeMessageExchange();
        FakeChatRoom chatRoom = new FakeChatRoom();
        User user = new RealUser(clientSocket, new RealChatProtocol());
        chatRoom.addSubscriber(user);
        Executors.newSingleThreadExecutor().submit(user::startConversation);

        user.forward("sample line one");
        clientSocket.newMessage("test message\n");
        user.forward("sample line two");

        chatRoom.waitForMessageThen(1000, TimeUnit.MILLISECONDS, () -> {
            assertThat(clientSocket.receivedMessage()).isEqualTo("sample line onesample line two");
            assertThat(chatRoom.receivedMessage()).isEqualTo("test message");
            assertThat(chatRoom.sentBy()).isEqualTo(user);
        });
    }

    @Test
    public void greetTheUser() {
        FakeMessageExchange clientSocket = new FakeMessageExchange();
        ChatProtocol protocol = new RealChatProtocol();
        User user = new RealUser(clientSocket, protocol);

        user.greet();

        String encodedMessage = protocol.addContent("Welcome to ChattyChat").encodeMessage();
        assertThat(clientSocket.receivedMessage()).isEqualTo(encodedMessage);
    }

    @Test
    public void askTheUserName() {
        FakeMessageExchange clientSocket = new FakeMessageExchange();
        ChatProtocol protocol = new RealChatProtocol();
        User user = new RealUser(clientSocket, protocol);

        Executors.newSingleThreadExecutor().submit(user::askUserName);

        clientSocket.waitForMessageThen(1000, () -> {
            String encodedMessage = protocol.addContent("Please enter your name").encodeMessage();
            assertThat(clientSocket.receivedMessage()).isEqualTo(encodedMessage);
        });
    }

    @Test
    public void registerTheUserName() {
        FakeMessageExchange clientSocket = new FakeMessageExchange();
        clientSocket.newMessage("Andrea\n");
        ChatProtocol protocol = new RealChatProtocol();
        User user = new RealUser(clientSocket, protocol);
        user.askUserName();

        Executors.newSingleThreadExecutor().submit(() -> {
            assertThat(user.getUserName()).isEqualTo("Andrea");
        });
    }
}