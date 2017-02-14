package com.andreamazzarella.chat_application;

import com.andreamazzarella.chat_server.Message;
import com.andreamazzarella.chat_server.User;
import com.andreamazzarella.support.FakeUser;
import org.junit.Test;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

public class ChatProtocolShould {

    @Test
    public void encodeAMessageWithNoUser() {
        ChatProtocol protocol = new ChatProtocol();
        Message message = new Message(Optional.empty(), "message from no particular user");

        assertThat(protocol.encodeMessage(message)).isEqualTo("~userName~~messageContent~message from no particular user");
    }



    @Test
    public void encodeAMessageWithUserAndContent() {
        User gigi = new FakeUser("Gigi Sabani");
        ChatProtocol protocol = new ChatProtocol();
        Message message = new Message(Optional.of(gigi), "Ciao da Napoli");

        assertThat(protocol.encodeMessage(message)).isEqualTo("~userName~Gigi Sabani~messageContent~Ciao da Napoli");
    }

    @Test
    public void decodeUserName() {
        User gigi = new FakeUser("Gigi Sabani");
        ChatProtocol protocol = new ChatProtocol();
        Message message = new Message(Optional.of(gigi), "Ciao da Napoli");

        String encodedMessage = protocol.encodeMessage(message);

        assertThat(protocol.decodeUserName(encodedMessage)).isEqualTo("Gigi Sabani");
    }

    @Test
    public void decodeMessageContent() {
        User gigi = new FakeUser("Gigi Sabani");
        ChatProtocol protocol = new ChatProtocol();
        Message message = new Message(Optional.of(gigi), "Ciao da Napoli");

        String encodedMessage = protocol.encodeMessage(message);

        assertThat(protocol.decodeMessageContent(encodedMessage)).isEqualTo("Ciao da Napoli");
    }

}
