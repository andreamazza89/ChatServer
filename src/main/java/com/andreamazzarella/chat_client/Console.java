package com.andreamazzarella.chat_client;

import com.andreamazzarella.chat_application.MessageExchange;
import com.andreamazzarella.chat_application.ChatProtocol;

import java.io.*;

public class Console implements MessageExchange {

    private final BufferedReader localInput;
    private final PrintStream localOutput;
    private final ChatProtocol protocol;

    Console(InputStream localInput, OutputStream localOutput, ChatProtocol protocol) {
        this.localInput = new BufferedReader(new InputStreamReader(localInput));
        this.localOutput = new PrintStream(localOutput);
        this.protocol = protocol;
    }

    @Override
    public void sendMessage(String rawMessage) {
        String userName = protocol.decodeUserName(rawMessage);
        String content = protocol.decodeMessageContent(rawMessage);
        String formattedOutput = prettyPrint(userName, content);
        localOutput.println(formattedOutput);
    }

    private String prettyPrint(String userName, String content) {
        if (userName.equals("")) {
            return content;
        } else {
            return userName + ": " + content;
        }
    }

    @Override
    public String readMessage() {
        try {
            return localInput.readLine();
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }
}
