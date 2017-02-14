package com.andreamazzarella.chat_server;

import com.andreamazzarella.support.FakeUser;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

public class InMemoryMessageRepositoryShould {

    @Test
    public void haveNoMessagesToStartWith() {
        MessageRepository repository = new InMemoryMessageRepository();

        assertThat(repository.all()).isEqualTo(new ArrayList<>());
    }

    @Test
    public void storeAMessage() {
        User andrea = new FakeUser("Andrea");
        Message message = new Message(Optional.of(andrea), "ciao da Roma");
        MessageRepository repository = new InMemoryMessageRepository();

        repository.add(message);

        Message storedMessage = repository.all().get(0);
        assertThat(storedMessage.getContent()).isEqualTo("ciao da Roma");
        assertThat(storedMessage.getUser().getUserName()).isEqualTo("Andrea");
    }

    @Test
    public void storeTwoMessages() {
        User andrea = new FakeUser("Andrea");
        User maria = new FakeUser("Maria");
        Message firstMessage = new Message(Optional.of(andrea), "ciao da Roma");
        Message secondMessage = new Message(Optional.of(maria), "hey there");
        MessageRepository repository = new InMemoryMessageRepository();

        repository.add(firstMessage);
        repository.add(secondMessage);

        Message storedMessageOne = repository.all().get(0);
        assertThat(storedMessageOne.getContent()).isEqualTo("ciao da Roma");
        assertThat(storedMessageOne.getUser().getUserName()).isEqualTo("Andrea");

        Message storedMessageTwo = repository.all().get(1);
        assertThat(storedMessageTwo.getContent()).isEqualTo("hey there");
        assertThat(storedMessageTwo.getUser().getUserName()).isEqualTo("Maria");
    }

}
