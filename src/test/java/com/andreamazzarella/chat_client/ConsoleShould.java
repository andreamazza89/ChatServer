package com.andreamazzarella.chat_client;

import com.andreamazzarella.chat_server.Message;
import com.andreamazzarella.chat_application.DataExchange;
import com.andreamazzarella.chat_application.ChatProtocol;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

public class ConsoleShould {
    //These tests highlight the fact that formatting the incoming raw message and printing it to the console should
    //be separate concerns (see the AND in the test descriptions).

    //I am leaving this as it is now because the formatting functionality is so small that introducing a collaborator seems
    //needless complexity

    @Test
    public void FormatANDPrintAMessageWithUserAndContent() {
        ByteArrayOutputStream consoleOutput = new ByteArrayOutputStream();
        ChatProtocol protocol = new ChatProtocol();
        Message messageWithUserAndContent = new Message(Optional.of("Andrea"), "Ciao!");
        String encodedMessage = protocol.encodeMessage(messageWithUserAndContent);
        DataExchange console = new Console(new ByteArrayInputStream("".getBytes()), consoleOutput, protocol);

        console.sendData(encodedMessage);

        assertThat(consoleOutput.toString()).isEqualTo("\u001B[34mAndrea\u001B[0m: Ciao!\n");
    }


    @Test
    public void FormatANDPrintAMessageWithContentOnly() {
        ByteArrayOutputStream consoleOutput = new ByteArrayOutputStream();
        ChatProtocol protocol = new ChatProtocol();
        Message messageWithContentOnly = new Message(Optional.empty(), "This is a message with no user name");
        String encodedMessage = protocol.encodeMessage(messageWithContentOnly);
        DataExchange console = new Console(new ByteArrayInputStream("".getBytes()), consoleOutput, protocol);

        console.sendData(encodedMessage);

        assertThat(consoleOutput.toString()).isEqualTo("This is a message with no user name\n");
    }
}
