package com.andreamazzarella.chat_server;

import com.andreamazzarella.chat_application.ChatProtocol;
import com.andreamazzarella.support.FakeUser;
import org.junit.Test;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

public class ChatRoomShould {

    @Test
    public void passMessageToAllClientsButTheOneWhoSentIt() {
        ChatProtocol protocol = new ChatProtocol();
        ChatRoom chatRoom = new ChatRoom(protocol, new InMemoryMessageRepository());
        FakeUser andrea = new FakeUser("andrea");
        FakeUser maria = new FakeUser("giorgio");
        chatRoom.addSubscriber(andrea);
        chatRoom.addSubscriber(maria);

        Message message = new Message(Optional.of(andrea), "Hello from andrea!");
        chatRoom.notifyDataReceivedFromClient("Hello from andrea!", andrea);

        String encodedMessage = protocol.encodeMessage(message);
        assertThat(andrea.receivedMessages()).isEqualTo("");
        assertThat(maria.receivedMessages()).isEqualTo(encodedMessage);
    }

    @Test
    public void passMultipleMessagesToMultipleClients() {
        ChatProtocol protocol = new ChatProtocol();
        ChatRoom chatRoom = new ChatRoom(protocol, new InMemoryMessageRepository());
        FakeUser andrea = new FakeUser("andrea");
        FakeUser maria = new FakeUser("maria");
        chatRoom.addSubscriber(andrea);
        chatRoom.addSubscriber(maria);

        Message andreasMessage = new Message(Optional.of(andrea), "Hello from andrea!");
        chatRoom.notifyDataReceivedFromClient("Hello from andrea!", andrea);
        Message mariasMessage = new Message(Optional.of(maria), "Hello from maria!");
        chatRoom.notifyDataReceivedFromClient("Hello from maria!", maria);

        String encodedMessageFromAndrea = protocol.encodeMessage(andreasMessage);
        String encodedMessageFromMaria = protocol.encodeMessage(mariasMessage);
        assertThat(andrea.receivedMessages()).isEqualTo(encodedMessageFromMaria);
        assertThat(maria.receivedMessages()).isEqualTo(encodedMessageFromAndrea);
    }

    @Test
    public void storeNewMessagesToTheGivenRepository() {
        MessageRepository repository = new InMemoryMessageRepository();
        ChatRoom chatRoom = new ChatRoom(new ChatProtocol(), repository);

        chatRoom.notifyDataReceivedFromClient("message", new FakeUser("andrea"));

        Message storedMessage = repository.all().get(0);
        assertThat(storedMessage.getContent()).isEqualTo("message");
        assertThat(storedMessage.getUser().getUserName()).isEqualTo("andrea");
    }

    @Test
    public void subscribeItselfToTheUser() {
        ChatRoom chatRoom = new ChatRoom(new ChatProtocol(), new InMemoryMessageRepository());
        FakeUser andrea = new FakeUser("andrea");

        chatRoom.addSubscriber(andrea);

        assertThat(andrea.subscribeToRoomWasCalled()).isTrue();
        assertThat(andrea.subscribeToRoomWasCalledWith()).isEqualTo(chatRoom);
    }

    @Test
    public void sendANewlySubscribedUserTheEntireChatHistory() { // this is not a sustainable solution: perhaps the chat protocol should be extended to encode multiple messages at once
        MessageRepository repository = new InMemoryMessageRepository();
        ChatProtocol protocol = new ChatProtocol();
        ChatRoom chatRoom = new ChatRoom(protocol, repository);
        FakeUser andrea = new FakeUser("andrea");
        Message messageOne = new Message(Optional.of(andrea), "hello there");
        Message messageTwo = new Message(Optional.of(andrea), "awkward, no one is here!");
        repository.add(messageOne);
        repository.add(messageTwo);

        chatRoom.addSubscriber(andrea);

        String messageHistory = protocol.encodeMessage(messageOne) + protocol.encodeMessage(messageTwo);
        assertThat(andrea.receivedMessages()).isEqualTo(messageHistory);
    }

}
