package com.andreamazzarella.chat_server;

import com.andreamazzarella.support.FakeUser;
import org.junit.Test;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

public class ChatRoomShould {

    @Test
    public void encodeMessageAndPassToAllClientsButTheOneWhoSentIt() {
        CommunicationProtocol protocol = new CommunicationProtocol();
        ChatRoom chatRoom = new ChatRoom(protocol);
        FakeUser andrea = new FakeUser("andrea");
        FakeUser maria = new FakeUser("giorgio");
        chatRoom.addSubscriber(andrea);
        chatRoom.addSubscriber(maria);

        chatRoom.notifyMessageFromClient("Hello from andrea!", andrea);

        String encodedMessage = protocol.messageFrom(andrea).withContent("Hello from andrea!").encode();
        assertThat(andrea.receivedMessage()).isEqualTo("");
        assertThat(maria.receivedMessage()).isEqualTo(encodedMessage);
    }

    @Test
    public void passMessagesToAllClientsButTheOneWhoSentIt() {
        CommunicationProtocol protocol = new CommunicationProtocol();
        ChatRoom chatRoom = new ChatRoom(protocol);
        FakeUser andrea = new FakeUser("andrea");
        FakeUser maria = new FakeUser("giorgio");
        chatRoom.addSubscriber(andrea);
        chatRoom.addSubscriber(maria);

        chatRoom.notifyMessageFromClient("Hello from andrea!", andrea);
        chatRoom.notifyMessageFromClient("Hello from maria!", maria);

        String encodedMessageFromAndrea = protocol.messageFrom(andrea).withContent("Hello from andrea!").encode();
        String encodedMessageFromMaria = protocol.messageFrom(maria).withContent("Hello from maria!").encode();
        assertThat(andrea.receivedMessage()).isEqualTo(encodedMessageFromMaria);
        assertThat(maria.receivedMessage()).isEqualTo(encodedMessageFromAndrea);
    }

    @Test
    public void subscribeItselfToTheUser() {
        ChatRoom chatRoom = new ChatRoom(new CommunicationProtocol());
        FakeUser andrea = new FakeUser("andrea");

        chatRoom.addSubscriber(andrea);

        assertThat(andrea.subscribeToRoomWasCalled()).isEqualTo(true);
    }

}
