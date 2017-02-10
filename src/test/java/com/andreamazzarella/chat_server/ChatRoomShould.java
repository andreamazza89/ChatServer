package com.andreamazzarella.chat_server;

import com.andreamazzarella.support.FakeUser;
import org.junit.Test;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

public class ChatRoomShould {

    @Test
    public void encodeMessageAndPassToAllClientsButTheOneWhoSentIt() {
        ChatProtocol protocol = new RealChatProtocol();
        ChatRoom chatRoom = new ChatRoom(protocol);
        FakeUser andrea = new FakeUser("andrea");
        FakeUser maria = new FakeUser("giorgio");
        chatRoom.addSubscriber(andrea);
        chatRoom.addSubscriber(maria);

        chatRoom.notifyMessageFromClient("Hello from andrea!", andrea);

        String encodedMessage = protocol.messageFrom(andrea).addContent("Hello from andrea!").encodeMessage();
        assertThat(andrea.receivedMessage()).isEqualTo("");
        assertThat(maria.receivedMessage()).isEqualTo(encodedMessage);
    }

    @Test
    public void passMessagesToAllClientsButTheOneWhoSentIt() {
        ChatProtocol protocol = new RealChatProtocol();
        ChatRoom chatRoom = new ChatRoom(protocol);
        FakeUser andrea = new FakeUser("andrea");
        FakeUser maria = new FakeUser("giorgio");
        chatRoom.addSubscriber(andrea);
        chatRoom.addSubscriber(maria);

        chatRoom.notifyMessageFromClient("Hello from andrea!", andrea);
        chatRoom.notifyMessageFromClient("Hello from maria!", maria);

        String encodedMessageFromAndrea = protocol.messageFrom(andrea).addContent("Hello from andrea!").encodeMessage();
        String encodedMessageFromMaria = protocol.messageFrom(maria).addContent("Hello from maria!").encodeMessage();
        assertThat(andrea.receivedMessage()).isEqualTo(encodedMessageFromMaria);
        assertThat(maria.receivedMessage()).isEqualTo(encodedMessageFromAndrea);
    }

    @Test
    public void subscribeItselfToTheUser() {
        ChatRoom chatRoom = new ChatRoom(new RealChatProtocol());
        FakeUser andrea = new FakeUser("andrea");

        chatRoom.addSubscriber(andrea);

        assertThat(andrea.subscribeToRoomWasCalled()).isTrue();
    }

}
