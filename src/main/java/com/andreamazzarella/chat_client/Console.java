package com.andreamazzarella.chat_client;

import com.andreamazzarella.chat_application.ChatProtocol;
import com.andreamazzarella.chat_application.DataExchange;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.UncheckedIOException;

public class Console implements DataExchange {

    private final BufferedReader localInput;
    private final PrintStream localOutput;
    private final ChatProtocol protocol;

    Console(InputStream localInput, OutputStream localOutput, ChatProtocol protocol) {
        this.localInput = new BufferedReader(new InputStreamReader(localInput));
        this.localOutput = new PrintStream(localOutput);
        this.protocol = protocol;
    }

    @Override
    public void sendData(String data) {
        String userName = protocol.decodeUserName(data);
        String content = protocol.decodeMessageContent(data);
        String formattedOutput = prettyPrint(userName, content);
        localOutput.println(formattedOutput);
    }

    private String prettyPrint(String userName, String content) {
        if (userName.equals("")) {
            return content;
        } else {
            return makeBlue(userName) + ": " + content;
        }
    }

    @Override
    public String readData() {
        try {
            return localInput.readLine();
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    private String makeBlue(String text){
        return "\u001B[34m" + text + "\u001B[0m";
    }
}
