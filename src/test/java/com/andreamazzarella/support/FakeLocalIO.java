package com.andreamazzarella.support;

import com.andreamazzarella.chat_client.LocalIO;

import java.io.*;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class FakeLocalIO implements LocalIO {

    private PipedOutputStream pipedOutputStream;
    private PipedInputStream pipedInputStream;
    private OutputStream outputStream = new MonitoredByteArrayOutputStream();
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

    public void waitForMessageThen(int timeOut, TimeUnit unit, Runnable callBack) {
        try {
            waitForMessage.await(timeOut, unit);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        callBack.run();
    }

    public void newMessage(String message) {
        try {
            pipedOutputStream.write(message.getBytes());
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    private class MonitoredByteArrayOutputStream extends ByteArrayOutputStream {

        @Override
        public synchronized void write(byte[] b, int off, int len) {
            waitForMessage.countDown();
            super.write(b, off, len);
        }

    }
}
