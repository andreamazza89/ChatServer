package com.andreamazzarella.chat_server;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.postgresql.ds.PGSimpleDataSource;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@RunWith(Parameterized.class)
public class MessageRepositoryShould {

    private final MessageRepository messageRepository;

    public MessageRepositoryShould(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    @Test
    public void haveNoMessagesToStartWith() {
        assertThat(messageRepository.all()).isEqualTo(new ArrayList<>());
    }

    @Test
    public void storeAMessage() {
        Message message = new Message(Optional.of("Andrea"), "ciao da Roma");

        messageRepository.add(message);

        Message storedMessage = messageRepository.all().get(0);
        assertThat(storedMessage.getContent()).isEqualTo("ciao da Roma");
        assertThat(storedMessage.getUserName()).isEqualTo(Optional.of("Andrea"));
    }

    @Test
    public void storeTwoMessages() {
        Message firstMessage = new Message(Optional.of("Andrea"), "ciao da Roma");
        Message secondMessage = new Message(Optional.of("Maria"), "hey there");
        MessageRepository repository = new InMemoryMessageRepository();

        repository.add(firstMessage);
        repository.add(secondMessage);

        Message storedMessageOne = repository.all().get(0);
        assertThat(storedMessageOne.getContent()).isEqualTo("ciao da Roma");
        assertThat(storedMessageOne.getUserName()).isEqualTo(Optional.of("Andrea"));

        Message storedMessageTwo = repository.all().get(1);
        assertThat(storedMessageTwo.getContent()).isEqualTo("hey there");
        assertThat(storedMessageTwo.getUserName()).isEqualTo(Optional.of("Maria"));
    }

    @After
    public void dropDatabaseTables() {
        PGSimpleDataSource source = new PGSimpleDataSource();
        source.setServerName("localhost");
        source.setDatabaseName("chat_server");
        source.setUser("Andrea");
        source.setPortNumber(5432);

        try (Connection connection = source.getConnection()) { // not sure if I should connect once instead of reastablishing a connection every time?
            Statement statement = connection.createStatement();
            statement.execute("DELETE FROM MESSAGES");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Parameterized.Parameters
    public static Collection<Object[]> instancesToTest() {
        return Arrays.asList(
                new Object[]{new InMemoryMessageRepository()},
                new Object[]{new SQLMessageRepository()}
        );
    }
}
