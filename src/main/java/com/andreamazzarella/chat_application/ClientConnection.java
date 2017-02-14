package com.andreamazzarella.chat_application;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.UncheckedIOException;
import java.net.Socket;

public class ClientConnection implements MessageExchange {

    private final BufferedReader reader;
    private final PrintStream writer;

    public ClientConnection(Socket rawClientSocket) {
        try {
            this.reader = new BufferedReader(new InputStreamReader(rawClientSocket.getInputStream()));
            this.writer = new PrintStream(rawClientSocket.getOutputStream());
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    @Override
    public void sendMessage(String message) {
        writer.println(message);
    }

    @Override
    public String readMessage() {
        try {
            return reader.readLine();
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }
}
