package com.andreamazzarella.support;

import com.andreamazzarella.chat_server.Connection;

import java.io.*;
import java.net.SocketAddress;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class FakeSocket implements Connection {

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
    public void connect(SocketAddress serverAddress) throws IOException {
        // pretend it just made a connection
    }

    @Override
    public InputStream getInputStream() throws IOException {
        return pipedInputStream;
    }

    @Override
    public OutputStream getOutputStream() {
        return outputStream;
    }

    public void waitForMessageThen(int timeOut, TimeUnit timeUnit, Runnable callback) {
        try {
            waitForMessage.await(timeOut, timeUnit);
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

    public String receivedMessages() {
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