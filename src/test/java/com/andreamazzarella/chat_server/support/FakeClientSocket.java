package com.andreamazzarella.chat_server.support;

import com.andreamazzarella.chat_server.ClientConnection;

import java.io.*;

public class FakeClientSocket implements ClientConnection {

    private OutputStream outputStream = new ByteArrayOutputStream();
    private InputStream inputStream;

    @Override
    public InputStream getInputStream() throws IOException {
        return inputStream;
    }

    @Override
    public OutputStream getOutputStream() throws IOException {
        return outputStream;
    }

    public String receivedMessages() {
        return outputStream.toString();
    }

    public void setMessage(String message) {
        inputStream = new ByteArrayInputStream(message.getBytes());
    }

}