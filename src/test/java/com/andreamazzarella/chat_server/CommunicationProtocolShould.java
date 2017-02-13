package com.andreamazzarella.chat_server;

import com.andreamazzarella.chat_application.ChatProtocol;
import com.andreamazzarella.chat_application.ChatProtocol;
import com.andreamazzarella.support.FakeUser;
import org.junit.Test;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

public class CommunicationProtocolShould {

    @Test
    public void encodeAUserName() {
        FakeUser gigi = new FakeUser("Gigi Sabani");
        ChatProtocol protocol = new ChatProtocol();

        protocol.messageFrom(gigi);

        assertThat(protocol.encodeMessage()).isEqualTo("~userName~Gigi Sabani~messageContent~");
    }

    @Test
    public void encodeAMessage() {
        ChatProtocol protocol = new ChatProtocol();

        protocol.addContent("sample message content");

        assertThat(protocol.encodeMessage()).isEqualTo("~userName~~messageContent~sample message content");
    }

    @Test
    public void encodeUserNameAndMessage() {
        FakeUser gigi = new FakeUser("Gigi Sabani");
        ChatProtocol protocol = new ChatProtocol();

        protocol.messageFrom(gigi).addContent("Ciao da Napoli");

        assertThat(protocol.encodeMessage()).isEqualTo("~userName~Gigi Sabani~messageContent~Ciao da Napoli");
    }

    @Test
    public void encodeMultipleTimes() {
        FakeUser gigi = new FakeUser("Gigi Sabani");
        ChatProtocol protocol = new ChatProtocol();

        protocol.messageFrom(gigi);
        String encodeOne = protocol.encodeMessage();

        protocol.addContent("Ciao da Milano");
        String encodeTwo = protocol.encodeMessage();

        assertThat(encodeOne).isEqualTo("~userName~Gigi Sabani~messageContent~");
        assertThat(encodeTwo).isEqualTo("~userName~~messageContent~Ciao da Milano");
    }

    @Test
    public void decodeUserName() {
        ChatProtocol protocol = new ChatProtocol();
        String encodedMessageOne = "~userName~Gigi Sabani~messageContent~Ciao da Napoli";
        String encodedMessageTwo = "~userName~Mara Venier~messageContent~Ciao da Napoli";

        assertThat(protocol.decodeUserName(encodedMessageOne)).isEqualTo("Gigi Sabani");
        assertThat(protocol.decodeUserName(encodedMessageTwo)).isEqualTo("Mara Venier");
    }

    @Test
    public void decodeMessageContent() {
        ChatProtocol protocol = new ChatProtocol();
        String encodedMessageOne = "~userName~Gigi Sabani~messageContent~Ciao da Napoli";
        String encodedMessageTwo = "~userName~Mara Venier~messageContent~Ciao da Milano";

        assertThat(protocol.decodeMessageContent(encodedMessageOne)).isEqualTo("Ciao da Napoli");
        assertThat(protocol.decodeMessageContent(encodedMessageTwo)).isEqualTo("Ciao da Milano");
    }

}
