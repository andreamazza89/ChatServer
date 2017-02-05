package com.andreamazzarella.chat_server.support;

import com.andreamazzarella.chat_server.ClientSocket;

import java.io.*;

public class FakeClientSocket implements ClientSocket {

    private OutputStream outputStream = new ByteArrayOutputStream();
    private PipedOutputStream pipedOutputStream;
    private PipedInputStream pipedInputStream;

    public FakeClientSocket() {
        this.pipedOutputStream = new PipedOutputStream();
        try {
            this.pipedInputStream = new PipedInputStream(pipedOutputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public InputStream getInputStream() throws IOException {
        return pipedInputStream;
    }

    @Override
    public OutputStream getOutputStream() throws IOException {
        return outputStream;
    }

    public String receivedMessages() {
        return outputStream.toString();
    }

    public void newMessage(String message) {
        try {
            pipedOutputStream.write(message.getBytes());
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

}