package com.andreamazzarella.chat_client;

import com.andreamazzarella.chat_server.ChatProtocol;

import java.io.*;

public class Console implements LocalIO {

    private final BufferedReader input;
    private final PrintStream output;
    private final ChatProtocol protocol;

    public Console(InputStream input, OutputStream output, ChatProtocol protocol) {
        this.input = new BufferedReader(new InputStreamReader(input));
        this.output = new PrintStream(output);
        this.protocol = protocol;
    }

    @Override
    public void displayMessage(String rawMessage) {
        String userName = protocol.decodeUserName(rawMessage);
        String content = protocol.decodeMessageContent(rawMessage);
        output.println(userName + ": " + content);
    }

    @Override
    public String readLine() {
        try {
            return input.readLine();
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }
}
