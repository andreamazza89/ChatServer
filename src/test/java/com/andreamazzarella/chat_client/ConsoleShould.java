package com.andreamazzarella.chat_client;

import com.andreamazzarella.chat_server.RealChatProtocol;
import com.andreamazzarella.chat_server.ChatProtocol;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

public class ConsoleShould {

    @Test
    public void PrintAGivenMessageToTheGivenOutputStream() {

        /////////////////////////////   MAKE PROTOCOL FAKE so this test does not need to know how it works
        ByteArrayOutputStream consoleOutput = new ByteArrayOutputStream();

        ChatProtocol protocol = new RealChatProtocol();
        LocalIO console = new Console(new ByteArrayInputStream("".getBytes()), consoleOutput, protocol);

        console.displayMessage("~userName~Andrea~messageContent~Ciao!");

        assertThat(consoleOutput.toString()).isEqualTo("Andrea: Ciao!\n");
    }
}
