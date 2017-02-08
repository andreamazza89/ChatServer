package com.andreamazzarella.support;

import com.andreamazzarella.chat_server.Connection;

import java.io.*;
import java.net.SocketAddress;

public class FakeSocket extends FakeIO implements Connection {

    @Override
    public void connect(SocketAddress serverAddress) throws IOException {
        // pretend it just made a connection (Has to be implemented as part of the Connection interface)
    }

    @Override
    public InputStream getInputStream() throws IOException {
        return pipedInputStream;
    }

    @Override
    public OutputStream getOutputStream() {
        return outputStream;
    }
}