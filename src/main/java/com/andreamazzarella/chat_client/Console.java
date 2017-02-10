package com.andreamazzarella.chat_client;

import com.andreamazzarella.chat_server.ChatProtocol;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;

public class Console implements LocalIO {

    private final InputStream input;
    private final PrintStream output;
    private final ChatProtocol protocol;

    public Console(InputStream input, OutputStream output, ChatProtocol protocol) {
        this.input = input;
        this.output = new PrintStream(output);
        this.protocol = protocol;
    }

    @Override
    public InputStream getInputStream() {
        return input;
    }

    @Override
    public OutputStream getOutputStream() {
        return output;
    }

    @Override
    public void addMessage(String rawMessage) {
        String userName = protocol.decodeUserName(rawMessage);
        String content = protocol.decodeMessageContent(rawMessage);
        output.println(userName + ": " + content);
    }
}
