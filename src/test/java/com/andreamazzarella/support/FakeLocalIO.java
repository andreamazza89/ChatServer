package com.andreamazzarella.support;

import com.andreamazzarella.chat_client.LocalIO;

import java.io.*;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class FakeLocalIO implements LocalIO {

    private PipedOutputStream pipedOutputStream;
    private PipedInputStream pipedInputStream;
    private OutputStream outputStream = new ByteArrayOutputStream();

    private CountDownLatch waitForMessage = new CountDownLatch(1);

    public FakeLocalIO() {
        this.pipedOutputStream = new PipedOutputStream();
        try {
            this.pipedInputStream = new PipedInputStream(pipedOutputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public InputStream getInputStream() {
        return pipedInputStream;
    }

    @Override
    public OutputStream getOutputStream() {
        return outputStream;
    }

    @Override
    public void addMessage(String message) {
        new PrintStream(outputStream).println(message);
        waitForMessage.countDown();
    }

    public void waitForMessageThen(int timeOut, Runnable callBack) {
        try {
            waitForMessage.await(timeOut, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        callBack.run();
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
