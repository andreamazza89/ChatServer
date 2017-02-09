package com.andreamazzarella.chat_server;

import com.andreamazzarella.support.FakeUser;
import org.junit.Test;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

public class CommunicationProtocolShould {

    @Test
    public void encodeAUserName() {
        FakeUser gigi = new FakeUser("Gigi Sabani");
        CommunicationProtocol protocol = new CommunicationProtocol();

        protocol.messageFrom(gigi);

        assertThat(protocol.encode()).isEqualTo("~userName~Gigi Sabani~messageContent~");
    }

    @Test
    public void encodeAMessage() {
        CommunicationProtocol protocol = new CommunicationProtocol();

        protocol.withContent("sample message content");

        assertThat(protocol.encode()).isEqualTo("~userName~~messageContent~sample message content");
    }

    @Test
    public void encodeUserNameAndMessage() {
        FakeUser gigi = new FakeUser("Gigi Sabani");
        CommunicationProtocol protocol = new CommunicationProtocol();

        protocol.messageFrom(gigi).withContent("Ciao da Napoli");

        assertThat(protocol.encode()).isEqualTo("~userName~Gigi Sabani~messageContent~Ciao da Napoli");
    }

    @Test
    public void encodeMultipleTimes() {
        FakeUser gigi = new FakeUser("Gigi Sabani");
        CommunicationProtocol protocol = new CommunicationProtocol();

        protocol.messageFrom(gigi);
        String encodeOne = protocol.encode();

        protocol.withContent("Ciao da Milano");
        String encodeTwo = protocol.encode();

        assertThat(encodeOne).isEqualTo("~userName~Gigi Sabani~messageContent~");
        assertThat(encodeTwo).isEqualTo("~userName~~messageContent~Ciao da Milano");
    }

    @Test
    public void decodeUserName() {
        CommunicationProtocol protocol = new CommunicationProtocol();
        String encodedMessageOne = "~userName~Gigi Sabani~messageContent~Ciao da Napoli";
        String encodedMessageTwo = "~userName~Mara Venier~messageContent~Ciao da Napoli";

        assertThat(protocol.decodeUserName(encodedMessageOne)).isEqualTo("Gigi Sabani");
        assertThat(protocol.decodeUserName(encodedMessageTwo)).isEqualTo("Mara Venier");
    }

    @Test
    public void decodeMessageContent() {
        CommunicationProtocol protocol = new CommunicationProtocol();
        String encodedMessageOne = "~userName~Gigi Sabani~messageContent~Ciao da Napoli";
        String encodedMessageTwo = "~userName~Mara Venier~messageContent~Ciao da Milano";

        assertThat(protocol.decodeMessageContent(encodedMessageOne)).isEqualTo("Ciao da Napoli");
        assertThat(protocol.decodeMessageContent(encodedMessageTwo)).isEqualTo("Ciao da Milano");
    }

}
