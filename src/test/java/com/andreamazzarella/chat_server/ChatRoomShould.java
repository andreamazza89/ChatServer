package com.andreamazzarella.chat_server;

import com.andreamazzarella.support.FakeSocket;
import org.junit.Test;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

public class ChatRoomShould {

    @Test
    public void passAMessageToAllClientsButTheOneWhoSentIt() {
        ChatRoom chatRoom = new ChatRoom();
        FakeSocket socketOne = new FakeSocket();
        FakeSocket socketTwo = new FakeSocket();
        FakeSocket socketThree = new FakeSocket();
        MessageExchanger messageExchangerOne = new MessageExchanger(socketOne, chatRoom);
        MessageExchanger messageExchangerTwo = new MessageExchanger(socketTwo, chatRoom);
        MessageExchanger messageExchangerThree = new MessageExchanger(socketThree, chatRoom);
        chatRoom.addSubscriber(messageExchangerOne);
        chatRoom.addSubscriber(messageExchangerTwo);
        chatRoom.addSubscriber(messageExchangerThree);

        chatRoom.notifyMessageFromClient("Hello from Socket One!", messageExchangerOne);

        assertThat(socketOne.receivedMessages()).isEqualTo("");
        assertThat(socketTwo.receivedMessages()).isEqualTo("Hello from Socket One!\n");
        assertThat(socketThree.receivedMessages()).isEqualTo("Hello from Socket One!\n");
    }

    @Test
    public void passMessagesToAllClientsButTheOneWhoSentIt() {
        ChatRoom chatRoom = new ChatRoom();
        FakeSocket socketOne = new FakeSocket();
        FakeSocket socketTwo = new FakeSocket();
        MessageExchanger messageExchangerOne = new MessageExchanger(socketOne, chatRoom);
        MessageExchanger messageExchangerTwo = new MessageExchanger(socketTwo, chatRoom);
        chatRoom.addSubscriber(messageExchangerOne);
        chatRoom.addSubscriber(messageExchangerTwo);

        chatRoom.notifyMessageFromClient("Hello from Socket One!", messageExchangerOne);
        chatRoom.notifyMessageFromClient("Hello from Socket Two!", messageExchangerTwo);

        assertThat(socketOne.receivedMessages()).isEqualTo("Hello from Socket Two!\n");
        assertThat(socketTwo.receivedMessages()).isEqualTo("Hello from Socket One!\n");
    }

    @Test
    public void provideAListOfConnectedClients() {
        ChatRoom chatRoom = new ChatRoom();
        FakeSocket socket = new FakeSocket();
        MessageExchanger messageExchanger = new MessageExchanger(socket, chatRoom);

        chatRoom.addSubscriber(messageExchanger);

        assertThat(chatRoom.connectedClients().get(0)).isEqualTo(messageExchanger);
    }
}
