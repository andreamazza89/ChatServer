package com.andreamazzarella.chat_server;

import com.andreamazzarella.chat_server.support.FakeClientSocket;
import org.junit.Test;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

public class ChatRoomShould {

    @Test
    public void passAMessageToAllClientsButTheOneWhoSentIt() {
        ChatRoom chatRoom = new ChatRoom();
        FakeClientSocket socketOne = new FakeClientSocket();
        FakeClientSocket socketTwo = new FakeClientSocket();
        FakeClientSocket socketThree = new FakeClientSocket();
        MessageExchanger messageExchangerOne = new MessageExchanger(socketOne, chatRoom);
        MessageExchanger messageExchangerTwo = new MessageExchanger(socketTwo, chatRoom);
        MessageExchanger messageExchangerThree = new MessageExchanger(socketThree, chatRoom);
        chatRoom.addMessageExchanger(messageExchangerOne);
        chatRoom.addMessageExchanger(messageExchangerTwo);
        chatRoom.addMessageExchanger(messageExchangerThree);

        chatRoom.messageFromClientNotification("Hello from Socket One!", messageExchangerOne);

        assertThat(socketOne.receivedMessages()).isEqualTo("");
        assertThat(socketTwo.receivedMessages()).isEqualTo("Hello from Socket One!\n");
        assertThat(socketThree.receivedMessages()).isEqualTo("Hello from Socket One!\n");
    }
}
