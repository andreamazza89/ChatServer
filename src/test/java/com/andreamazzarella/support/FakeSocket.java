package com.andreamazzarella.support;

import com.andreamazzarella.chat_application.MessageExchange;

import java.io.*;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class FakeSocket implements MessageExchange {

    private PipedOutputStream pipedOutputStream;
    private PipedInputStream pipedInputStream;
    private OutputStream outputStream = new MonitoredByteArrayOutputStream();

    private CountDownLatch waitForMessage = new CountDownLatch(1);

    public FakeSocket() {
        this.pipedOutputStream = new PipedOutputStream();
        try {
            this.pipedInputStream = new PipedInputStream(pipedOutputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void sendMessage(String message) {
        try {
            outputStream.write(message.getBytes());
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    @Override
    public String readMessage() {
        try {
            return new BufferedReader(new InputStreamReader(pipedInputStream)).readLine();
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    public void waitForMessageThen(int timeOut, Runnable callback) {
        try {
            waitForMessage.await(timeOut, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        callback.run();
    }

    public void newMessage(String message) {
        try {
            pipedOutputStream.write(message.getBytes());
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    public String receivedMessage() {
        return outputStream.toString();
    }

    private class MonitoredByteArrayOutputStream extends ByteArrayOutputStream {

        @Override
        public synchronized void write(byte[] b, int off, int len) {
            waitForMessage.countDown();
            super.write(b, off, len);
        }

    }
}