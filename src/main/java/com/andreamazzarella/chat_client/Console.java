package com.andreamazzarella.chat_client;

import com.andreamazzarella.chat_application.MessageExchange;
import com.andreamazzarella.chat_server.ChatProtocol;

import java.io.*;

public class Console implements MessageExchange {

    private final BufferedReader input;
    private final PrintStream output;
    private final ChatProtocol protocol;

    Console(InputStream input, OutputStream output, ChatProtocol protocol) {
        this.input = new BufferedReader(new InputStreamReader(input));
        this.output = new PrintStream(output);
        this.protocol = protocol;
    }

    @Override
    public void sendMessage(String rawMessage) {
        String userName = protocol.decodeUserName(rawMessage);
        String content = protocol.decodeMessageContent(rawMessage);
        output.println(userName + ": " + content);
    }

    @Override
    public String readMessage() {
        try {
            return input.readLine();
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }
}
