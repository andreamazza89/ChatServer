package com.andreamazzarella.support;

import java.io.*;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class FakeIO {

    OutputStream outputStream = new MonitoredByteArrayOutputStream();
    private PipedOutputStream pipedOutputStream;
    PipedInputStream pipedInputStream;
    private CountDownLatch waitForMessage = new CountDownLatch(1);

    FakeIO() {
        this.pipedOutputStream = new PipedOutputStream();
        try {
            this.pipedInputStream = new PipedInputStream(pipedOutputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
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

    public void waitForMessageThen(int timeOut, TimeUnit timeUnit, Runnable callback) {
        try {
            waitForMessage.await(timeOut, timeUnit);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        callback.run();
    }

    private class MonitoredByteArrayOutputStream extends ByteArrayOutputStream {

        @Override
        public synchronized void write(byte[] b, int off, int len) {
            waitForMessage.countDown();
            super.write(b, off, len);
        }
    }
}
